package com.code.factory.services;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.factory.domain.LoginCredentials;
import com.code.factory.domain.UserProfileEntity;
import com.code.factory.repositories.UserProfileRepository;
import com.code.factory.util.AuthenticationUtil;

@Service("authenticationService")
public class AuthenticationService{
    @Autowired
    UserProfileRepository userProfileRepository;

    AuthenticationUtil    authenticationUtil;

    @Autowired
    public AuthenticationService(final AuthenticationUtil authenticationUtil){
        this.authenticationUtil = authenticationUtil;
    }

    public UserProfileEntity authenticate(final String userName, final String userPassword)
            throws AuthenticationException{
        final UserProfileEntity userEntity = this.getUserProfile(userName); // User name must be unique in our system
        // Here we perform authentication business logic
        // If authentication fails, we throw new AuthenticationException
        // otherwise we return UserProfile Details
        String secureUserPassword = null;
        try{
            if(userEntity != null){
                secureUserPassword = this.authenticationUtil.generateSecurePassword(userPassword, userEntity.getSalt());
            }
        }
        catch(final InvalidKeySpecException ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException(ex.getLocalizedMessage());
        }
        boolean authenticated = false;
        if(secureUserPassword != null && secureUserPassword.equalsIgnoreCase(userEntity.getToken())){
            if(userName != null && userName.equalsIgnoreCase(userEntity.getUserName())){
                authenticated = true;
            }
        }
        if(!authenticated){
            throw new AuthenticationException("Authentication failed");
        }
        return userEntity;
    }

    public UserProfileEntity resetSecurityCridentials(final String password, final UserProfileEntity userProfile)
            throws AuthenticationException{
        // Generate salt
        final String salt = this.authenticationUtil.generateSalt(30);
        // Generate secure user password
        String secureUserPassword = null;
        try{
            secureUserPassword = this.authenticationUtil.generateSecurePassword(password, salt);
        }
        catch(final InvalidKeySpecException ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException(ex.getLocalizedMessage());
        }
        userProfile.setSalt(salt);
        userProfile.setUserPassword(secureUserPassword);
        // Connect to database
        try{
            this.userProfileRepository.save(userProfile);
        }
        catch(final Exception ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userProfile;
    }

    public String issueSecureToken(final UserProfileEntity userProfile) throws AuthenticationException{
        String returnValue = null;
        // Get salt but only part of it
        final String newSaltAsPostfix = userProfile.getSalt();
        final String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;
        byte[] encryptedAccessToken = null;
        try{
            encryptedAccessToken = this.authenticationUtil.encrypt(userProfile.getUserPassword(), accessTokenMaterial);
        }
        catch(final InvalidKeySpecException ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Failed to issue secure access token");
        }
        final String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
        // Split token into equal parts
        final int tokenLength = encryptedAccessTokenBase64Encoded.length();
        final String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);
        userProfile.setToken(tokenToSaveToDatabase);
        this.storeAccessToken(userProfile);
        return returnValue;
    }

    public UserProfileEntity checkUserAuthentication(final LoginCredentials loginCredentials){
        UserProfileEntity userProfile = null;
        try{
            userProfile = this.userProfileRepository.findByUserName(loginCredentials.getUserName());
            if(userProfile != null && userProfile.getToken().equals(loginCredentials.getPassword())){
                return userProfile;
            }
        }
        catch(final Exception ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void storeAccessToken(final UserProfileEntity userProfile){
        // Connect to database
        try{
            this.userProfileRepository.save(userProfile);
        }
        catch(final Exception ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private UserProfileEntity getUserProfile(final String userName){
        UserProfileEntity returnValue = null;
        try{
            returnValue = this.userProfileRepository.findByUserName(userName);
        }
        catch(final Exception ex){
            Logger.getLogger(AuthenticationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnValue;
    }
}