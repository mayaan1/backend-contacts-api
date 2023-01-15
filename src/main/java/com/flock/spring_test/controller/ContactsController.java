package com.flock.spring_test.controller;

import com.flock.spring_test.model.Contacts;
import com.flock.spring_test.service.ContactsService;
import com.flock.spring_test.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/contact")
@RestController
public class ContactsController {
    @Autowired
    ContactsService contactsService;

    @Autowired
    UserLoginService userLoginService;


    @PostMapping("/add")
    public Contacts addUser(@RequestParam Map<String,String> param,
                            @RequestHeader Map<String,String> header,
                            @RequestBody Contacts contacts) {
        String token = param.get("token");
        if(token == null )
            token = String.valueOf(userLoginService.getUserToken(header.get("username"), header.get("password")));
        contacts.setUid(token);
        contacts.setScore(0);
        return contactsService.addContact(contacts);
    }

    @GetMapping("/showAll")
    List<Contacts> showAll(@RequestParam Map<String,String> param,
                           @RequestHeader Map<String,String> header) {
        String token = param.get("token");
        if(token == null )
            token = String.valueOf(userLoginService.getUserToken(header.get("username"), header.get("password")));
        return contactsService.showAllContacts(token);
    }

    @GetMapping("/lookUp")
    List<Contacts> lookUp(@RequestParam String lookUpText ) {
        return contactsService.lookUp(lookUpText);
    }

    @GetMapping("/showContacts")
    List<Contacts> showContactsForUID(@RequestParam String token) {
        Contacts contact = new Contacts();
        contact.setUid(token);
        return contactsService.showContactsForUID(contact);
    }
}
