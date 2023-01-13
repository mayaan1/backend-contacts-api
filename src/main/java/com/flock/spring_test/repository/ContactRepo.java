package com.flock.spring_test.repository;

import com.flock.spring_test.model.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public Contacts addContact(Contacts contact) {
        String sql = "Insert into Contacts (uid,contact_id,contact_name,score) Values(?,?,?,?)";
        try {
            jdbcTemplate.update(sql, contact.getUid(),contact.getContact_id(), contact.getContact_name(), contact.getScore());
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return contact;
    }

    public List<Contacts> showAllContacts() {
        return jdbcTemplate.query("SELECT * FROM contacts", (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contact_id"),
                        rs.getString("contact_name"),
                        rs.getInt("score")
                ));
    }

    public List<Contacts> showContactsForUID(Contacts contact) {
        String sql = "SELECT * FROM contacts where UID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contact_id"),
                        rs.getString("contact_name"),
                        rs.getInt("score")
                ), contact.getUid());
    }

    public List<Contacts> lookUp(String lookUpText) {
        String sql = "SELECT * FROM contacts where contact_name like ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contact_id"),
                        rs.getString("contact_name"),
                        rs.getInt("score")
                ), lookUpText + "%");
    }
}
