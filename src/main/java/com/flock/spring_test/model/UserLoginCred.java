package com.flock.spring_test.model;

import java.util.UUID;

public class UserLoginCred {
    private String username;
    private String password;
    private String token;

    public UserLoginCred() {
    }

    public UserLoginCred(String username, String password, String token) {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

}
