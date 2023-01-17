package com.flock.spring_test.controller;

import com.flock.spring_test.model.Contacts;
import com.flock.spring_test.service.ContactsService;
import com.flock.spring_test.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
        String username = header.get("username");
        if( username == null ) username = userLoginService.getUsernameFromToken(param.get("token"));
        contacts.setUid(username);
        contacts.setScore(0);

        return contactsService.addContact(contacts);
    }

    @GetMapping("/showAll")
    List<Contacts> showAll(@RequestParam Map<String,String> param,
                           @RequestHeader Map<String,String> header) {
        String username = header.get("username");
        if( username == null ) username = userLoginService.getUsernameFromToken(param.get("token"));

        return contactsService.showAllContacts(username);
    }

    @GetMapping("/showAllContactsByScore")
    List<Contacts> showAllContactsByScore(@RequestParam Map<String,String> param,
                           @RequestHeader Map<String,String> header) {
        String username = header.get("username");
        if( username == null ) username = userLoginService.getUsernameFromToken(param.get("token"));

        return contactsService.showAllContactsByScore(username);
    }

    @GetMapping("/lookUp")
    List<Contacts> lookUp(@RequestParam Map<String,String> param,
                          @RequestHeader Map<String,String> header) {
        String username = header.get("username");
        if( username == null ) username = userLoginService.getUsernameFromToken(param.get("token"));
        return contactsService.lookUp(param.get("lookUpText"), username);
    }

    @GetMapping("/viewSingleContact")
    Contacts viewContact(@RequestParam Map<String,String> param,
                         @RequestHeader Map<String,String> header) {
        String username = header.get("username");
        if( username == null ) username = userLoginService.getUsernameFromToken(param.get("token"));
        Contacts contact = new Contacts();
        contact.setUid(username); contact.setContactUID(param.get("contactUID"));
        return contactsService.viewSingleContact(contact);
    }
}
