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
    public Contacts addUser(@RequestParam String token, @RequestBody Contacts contacts) {
        contacts.setUid(token);
        contacts.setScore(0);
        return contactsService.addContact(contacts);
    }

    @GetMapping("/showAll")
    List<Contacts> showAll(@RequestParam String token) {
        return contactsService.showAllContacts();
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
