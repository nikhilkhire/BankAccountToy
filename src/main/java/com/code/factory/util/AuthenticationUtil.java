package com.code.factory.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil{
    private static final Random RANDOM     = new SecureRandom();
    private static final String ALPHABET   = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int    ITERATIONS = 10000;
    private static final int    KEY_LENGTH = 256;

    public String generateSalt(final int length){
        final StringBuilder returnValue = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            returnValue.append(
                    AuthenticationUtil.ALPHABET
                            .charAt(AuthenticationUtil.RANDOM.nextInt(AuthenticationUtil.ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public String generateUserId(final int length){
        return this.generateSalt(length);
    }

    public String generateSecurePassword(final String password, final String salt) throws InvalidKeySpecException{
        final byte[] securePassword = this.hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    private byte[] hash(final char[] password, final byte[] salt) throws InvalidKeySpecException{
        final PBEKeySpec spec = new PBEKeySpec(
                password,
                salt,
                AuthenticationUtil.ITERATIONS,
                AuthenticationUtil.KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try{
            final SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }
        catch(final NoSuchAlgorithmException | InvalidKeySpecException e){
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        }
        finally{
            spec.clearPassword();
        }
    }

    public SecretKey generateSecretKey() throws NoSuchAlgorithmException{
        SecretKey returnValue = null;
        final KeyGenerator secretKeyGenerator = KeyGenerator.getInstance("DESede");
        secretKeyGenerator.init(112);
        returnValue = secretKeyGenerator.generateKey();
        return returnValue;
    }

    public byte[] encrypt(final String securePassword, final String accessTokenMaterial) throws InvalidKeySpecException{
        return this.hash(securePassword.toCharArray(), accessTokenMaterial.getBytes());
    }

}