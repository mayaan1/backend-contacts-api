package com.flock.spring_test.controller;

import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.repository.UserLoginRepo;
import com.flock.spring_test.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class UserLoginController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserLoginService userLogin;
    @RequestMapping({"/user"})
    public String helloUser(){
        return "Hello User";
    }

    @RequestMapping({"/admin"})
    public String helloAdmin(){
        String sql = "insert into users values (1,9899,\"Add\",\"a@gmail.com\",\"A\")";
        jdbcTemplate.update(sql);
        return "Hello Admin";
    }

    @GetMapping({"/getAllUserCred"})
    public List<UserLoginCred> getAllUserLoginCred() {
        return userLogin.getAllUsers();
    }

    @PostMapping("/add")
    public UserLoginCred addUser(@RequestBody UserLoginCred user) {
        return userLogin.addUser(user);
    }

    @GetMapping("/getToken")
    public UserLoginCred getToken(@RequestBody UserLoginCred user ) {
        return userLogin.getUserToken(user);
    }

    @PostMapping("/delete")
    public UserLoginCred deleteUser(@RequestBody UserLoginCred user ) {
        return userLogin.deleteUser(user);
    }
}
