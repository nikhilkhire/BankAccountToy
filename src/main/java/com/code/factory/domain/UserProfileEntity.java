package com.code.factory.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "Profile")
public class UserProfileEntity implements Serializable{
    private static final long serialVersionUID = 7290798953394355234L;
    @Id
    @GeneratedValue
    private long              id;
    @Column(name = "first_name")
    private String            firstName;
    @Column(name = "last_name")
    private String            lastName;
    @Column(name = "userid")
    private String            userId;
    @Column(name = "salt")
    private String            salt;
    @Column(name = "token")
    private String            token;
    @Column(name = "password")
    private String            userPassword;
    @Column(name = "user_name")
    private String            userName;
    @OneToMany
    private List<Account>     accountList;

    /**
     * @return the firstName
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName){
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName){
        this.lastName = lastName;
    }

    /**
     * @return the id
     */
    public long getId(){
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final long id){
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

    /**
     * @return the userId
     */
    public String getUserId(){
        return this.userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final String userId){
        this.userId = userId;
    }

    /**
     * @return the salt
     */
    public String getSalt(){
        return this.salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(final String salt){
        this.salt = salt;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword(){
        return this.userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(final String userPassword){
        this.userPassword = userPassword;
    }

    /**
     * @return the userName
     */
    public String getUserName(){
        return this.userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(final String userName){
        this.userName = userName;
    }

    /**
     * @return List<Account>
     */
    public List<Account> getAccountList(){
        return this.accountList;
    }

    /**
     * @param accountList
     */
    public void setAccountList(final List<Account> accountList){
        this.accountList = accountList;
    }
}