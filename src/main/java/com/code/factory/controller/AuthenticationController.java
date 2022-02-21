package com.code.factory.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.code.factory.domain.AuthenticationDetails;
import com.code.factory.domain.LoginCredentials;
import com.code.factory.domain.UserProfileEntity;

@RestController()
public class AuthenticationController extends BaseController{

    @PostMapping(value = "/authentication", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuthenticationDetails userLogin(@RequestBody final LoginCredentials loginCredentials)
            throws AuthenticationException{
        final AuthenticationDetails returnValue = new AuthenticationDetails();

        UserProfileEntity userProfile = null;

        // Perform User Authentication
        try{
            userProfile = this.authenticationService
                    .authenticate(loginCredentials.getUserName(), loginCredentials.getPassword());
        }
        catch(final AuthenticationException ex){
            Logger.getLogger(AuthenticationController.class.getName()).log(Level.SEVERE, null, ex);
            return returnValue;
        }

        // Reset Access Token
        this.authenticationService.resetSecurityCridentials(loginCredentials.getUserName(), userProfile);

        // Issue a new Access token
        final String secureUserToken = this.authenticationService.issueSecureToken(userProfile);

        returnValue.setToken(secureUserToken);
        returnValue.setId(userProfile.getUserId());

        return returnValue;
    }
}