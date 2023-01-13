package com.flock.spring_test.service;

import com.flock.spring_test.model.Contacts;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsService {
    @Autowired
    ContactRepo contactRepo;
    public Contacts addContact(Contacts contacts) {
        return contactRepo.addContact(contacts);
    }

    public List<Contacts> showAllContacts() {
        return contactRepo.showAllContacts();
    }

    public List<Contacts> showContactsForUID(Contacts contact) {
        return contactRepo.showContactsForUID(contact);
    }

    public List<Contacts> lookUp(String lookUpText) {
        return contactRepo.lookUp(lookUpText);
    }
}