package com.flock.spring_test.controller;

import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.service.UserContactService;
import com.flock.spring_test.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserContactController {
    @Autowired
    UserContactService userContactService;

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("/update")
    public UserContact updateContact(@RequestParam Map<String,String> param,
                                     @RequestHeader Map<String,String> header,
                                     @RequestBody UserContact user) {
        String username = header.get("username");
        if( username == null ) username = userLoginService.getUsernameFromToken(param.get("token"));
        user.setUid(username);
        if( user.getAddress() != null ) userContactService.updateUserAddress(user);
        if( user.getMobileNo() != null ) userContactService.updateUserMobileNo(user);
        if( user.getName() != null ) userContactService.updateUserName(user);
        if( user.getEmail() != null ) userContactService.updateUserEmail(user);
        return user;
    }

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
