package com.flock.spring_test.controller;

import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.service.UserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserContactController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserContactService userContactService;

    @PostMapping("/add")
    public UserContact addUser(@RequestBody UserContact user) {
        return userContactService.addUser(user);
    }

    @GetMapping("/getAllUsers")
    public List<UserContact> getAllUsers() {
        return userContactService.getAllUsers();
    }

    @PostMapping("/delete")
    public UserContact deleteUser(@RequestBody UserContact user) {
        return userContactService.deleteUser(user);
    }
}
