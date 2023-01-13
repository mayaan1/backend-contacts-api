package com.flock.spring_test.controller;

import com.flock.spring_test.model.Contacts;
import com.flock.spring_test.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/contact")
@RestController
public class ContactsController {
    @Autowired
    ContactsService contactsService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/add")
    public Contacts addUser(@RequestBody Contacts contacts) {
        return contactsService.addContact(contacts);
    }

    @GetMapping("/showAll")
    List<Contacts> showAll(@RequestBody Contacts contacts) {
        return contactsService.showAllContacts();
    }

    @GetMapping("/lookUp")
    List<Contacts> lookUp(@RequestParam String lookUpText ) {
        return contactsService.lookUp(lookUpText);
    }

    @GetMapping("/showContacts")
    List<Contacts> showContactsForUID(@RequestBody Contacts contacts) {
        return contactsService.showContactsForUID(contacts);
    }
}
