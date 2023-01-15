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
        String sql = "Insert into Contacts (uid,contactuid,contactname,score) Values(?,?,?,?)";
        try {
            jdbcTemplate.update(sql, contact.getUid(),contact.getContactUID(), contact.getContactName(), contact.getScore());
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return contact;
    }

    public List<Contacts> showAllContacts(String uid) {
        return jdbcTemplate.query("SELECT * FROM contacts where uid = ?", (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactuid"),
                        rs.getString("contactname"),
                        rs.getInt("score")
                ), uid);
    }

    public List<Contacts> showContactsForUID(Contacts contact) {
        String sql = "SELECT * FROM contacts where UID = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactid"),
                        rs.getString("contactname"),
                        rs.getInt("score")
                ), contact.getUid());
    }

    public List<Contacts> lookUp(String lookUpText) {
        String sql = "SELECT * FROM contacts where contact_name like ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactid"),
                        rs.getString("contactname"),
                        rs.getInt("score")
                ), lookUpText + "%");
    }
}
