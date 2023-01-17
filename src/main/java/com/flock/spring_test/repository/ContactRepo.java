package com.flock.spring_test.repository;

import com.flock.spring_test.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.flock.spring_test.mappers.Mappers.*;
import static com.flock.spring_test.repository.sqlConstants.Contacts.*;

@Repository
public class ContactRepo {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Contacts addContact(Contacts contact) {
        try {
            namedParameterJdbcTemplate.update(INSERT_INTO_CONTACTS, getContactMap(contact));
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return contact;
    }

    public List<Contacts> showAllContacts(String uid) {
        return namedParameterJdbcTemplate.query(SELECT_CONTACT_UID, getUidMap(uid), CONTACT_RM);
    }

    public List<Contacts> showContactsForUID(Contacts contact) {
        return namedParameterJdbcTemplate.query(SELECT_CONTACT_UID, getContactMap(contact), CONTACT_RM);
    }

    public List<Contacts> lookUp(String lookUpText, String uid) {
        if(lookUpText == null ) {
            return namedParameterJdbcTemplate.query(SELECT_CONTACT_UID_ORDER, getUidMap(uid), CONTACT_RM);
        }
        return namedParameterJdbcTemplate.query(SELECT_CONTACT_UID_CONTACTNAME_ORDER, getUidLookUpMap(uid, lookUpText), CONTACT_RM);
    }

    public Contacts viewSingleContact(Contacts contact) {
        namedParameterJdbcTemplate.update(UPDATE_CONTACT_SCORE_UID_CONTACTID, getContactMap(contact));
        return namedParameterJdbcTemplate.queryForObject(SELECT_CONTACT_UID_CONTACTID, getContactMap(contact), CONTACT_RM);
    }

    public List<Contacts> showAllContactsByScore(String uid) {
        return namedParameterJdbcTemplate.query(SELECT_CONTACT_UID_ORDER_SCORE_NAME, getUidMap(uid), CONTACT_RM);
    }
}
