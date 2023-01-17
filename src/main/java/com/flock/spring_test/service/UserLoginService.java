package com.flock.spring_test.service;

import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.repository.UserLoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginService {
    @Autowired
    UserLoginRepo userLoginRepo;

    public boolean authenticateUser(String username, String password) {
        String passwordInDB = userLoginRepo.getPasswordForUsername(username);
        return passwordInDB.equals(password);
    }
    public boolean authenticateUser(String token) {
        return userLoginRepo.isTokenPresent(token);
    }
    public List<UserLoginCred> getAllUsers() {
        return userLoginRepo.getAllUsers();
    }

    public UserLoginCred addUser(UserLoginCred user) {
        user.setToken(user.generateToken());
        UserContact userContact = new UserContact();
        userContact.setUid(user.getUsername());
        return userLoginRepo.addUser(user, userContact);
    }

    public UserLoginCred deleteUser(UserLoginCred user ) {
        return userLoginRepo.deleteUser(user);
    }

    public UserLoginCred getUserToken(UserLoginCred user) {
        return userLoginRepo.getUserToken(user);
    }

    public String getPasswordForUsername(String username) {
        return userLoginRepo.getPasswordForUsername(username);
    }

    public String getUsernameFromToken(String token) {
        return userLoginRepo.getUsernameFromToken(token);
    }
}
