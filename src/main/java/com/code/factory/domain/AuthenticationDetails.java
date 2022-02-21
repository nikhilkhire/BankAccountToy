package com.code.factory.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthenticationDetails{
    private String id;
    private String token;

    /**
     * @return the id
     */
    public String getId(){
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final String id){
        this.id = id;
    }

    /**
     * @return the token
     */
    public String getToken(){
        return this.token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(final String token){
        this.token = token;
    }
}