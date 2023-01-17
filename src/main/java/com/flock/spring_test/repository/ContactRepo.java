package com.flock.spring_test.repository;

import com.flock.spring_test.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NavigableMap;

import static com.flock.spring_test.mappers.Mappers.*;

@Repository
public class ContactRepo {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Contacts addContact(Contacts contact) {
        String sql = "Insert into Contacts (uid,contactUID,contactName,score) Values(:uid,:contactUID,:contactName,:score)";
        try {
            namedParameterJdbcTemplate.update(sql, getContactMap(contact));
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return contact;
    }

    public List<Contacts> showAllContacts(String uid) {
        String sql = "SELECT * FROM contacts where uid = :uid";
        return namedParameterJdbcTemplate.query(sql, getUidMap(uid), CONTACT_RM);
    }

    public List<Contacts> showContactsForUID(Contacts contact) {
        String sql = "SELECT * FROM contacts where UID = :uid";
        return namedParameterJdbcTemplate.query(sql, getContactMap(contact), CONTACT_RM);
    }

    public List<Contacts> lookUp(String lookUpText, String uid) {
        String sql = "SELECT * FROM contacts where UID = :uid and contactName like :contactName order by score desc";
        if(lookUpText == null ) {
            sql = "SELECT * FROM contacts where UID = :uid order by score desc";
            return namedParameterJdbcTemplate.query(sql, getUidMap(uid), CONTACT_RM);
        }
        return namedParameterJdbcTemplate.query(sql, getUidLookUpMap(uid, lookUpText), CONTACT_RM);
    }

    public Contacts viewSingleContact(Contacts contact) {
        String sql = "SELECT * FROM contacts where UID = :uid and contactUID = :contactUID";
        String updateSql = "Update contacts set score = score + 1 where UID = :uid and contactUID = :contactUID";
        namedParameterJdbcTemplate.update(updateSql, getContactMap(contact));
        return namedParameterJdbcTemplate.queryForObject(sql, getContactMap(contact), CONTACT_RM);
    }

    public List<Contacts> showAllContactsByScore(String uid) {
        String sql = "SELECT * FROM contacts where UID = :uid order by score desc, contactName";
        return namedParameterJdbcTemplate.query(sql, getUidMap(uid), CONTACT_RM);
    }
}
