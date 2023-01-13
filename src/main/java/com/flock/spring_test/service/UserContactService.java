package com.flock.spring_test.service;

import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.repository.UserContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserContactService {
    @Autowired
    UserContactRepo userContactRepo;

    public List<UserContact> getAllUsers() {
        return userContactRepo.getAllUsers();
    }

    public UserContact addUser(UserContact user) {
        return userContactRepo.addUser(user);
    }

    public UserContact deleteUser(UserContact user) {
        return userContactRepo.deleteUserID(user);
    }

}
