package com.example.myapp;

import com.google.firebase.database.IgnoreExtraProperties;

public class User {
    private String UserId;
    private String UserName;
    private String UserAddr;
    private String UserEmail;
    private String UserPass;
    private String UserMobile;
    private String UserBit;

    public User(){

    }

    public User( String UserId, String UserName, String UserAddr, String UserEmail, String UserPass, String UserMobile, String UserBit){
        this.UserId = UserId;
        this.UserName = UserName;
        this.UserAddr =UserAddr;
        this.UserEmail = UserEmail;
        this.UserPass = UserPass;
        this.UserMobile = UserMobile;
        this.UserBit = UserBit;
    }
    public String getUserId(){
        return UserId;
    }
    public String getUserName(){
        return UserName;
    }
    public String getUserAddr(){
        return UserAddr;
    }
    public String getUserEmail(){
        return UserEmail;
    }
    public String getUserPass(){
        return UserPass;
    }
    public String getUserMobile(){
        return UserMobile;
    }
    public String getUserBit(){
        return UserBit;
    }
}
