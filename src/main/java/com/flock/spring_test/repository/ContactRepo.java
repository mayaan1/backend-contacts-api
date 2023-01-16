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
        String sql = "Insert into Contacts (uid,contactUid,contactName,score) Values(?,?,?,?)";
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
                        rs.getString("contactUid"),
                        rs.getString("contactName"),
                        rs.getInt("score")
                ), uid);
    }

    public List<Contacts> showContactsForUID(Contacts contact) {
        String sql = "SELECT * FROM contacts where UID = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactUid"),
                        rs.getString("contactName"),
                        rs.getInt("score")
                ), contact.getUid());
    }

    public List<Contacts> lookUp(String lookUpText, String Uid) {
        String sql = "SELECT * FROM contacts where UID = ? and contactName like ? order by score";
        if(lookUpText == null ) {
            sql = "SELECT * FROM contacts where UID = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Contacts(
                            rs.getString("uid"),
                            rs.getString("contactUid"),
                            rs.getString("contactName"),
                            rs.getInt("score")
                    ), Uid);
        }
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactUid"),
                        rs.getString("contactName"),
                        rs.getInt("score")
                ), Uid, lookUpText + "%");
    }

    public Contacts viewSingleContact(String contactUID, String Uid) {
        String sql = "SELECT * FROM contacts where UID = ? and contactUID = ?";
        jdbcTemplate.update("Update contacts set score = score + 1 where UID = ? and contactUID = ?", Uid, contactUID);
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactUid"),
                        rs.getString("contactName"),
                        rs.getInt("score")
                ), Uid, contactUID);
    }

    public List<Contacts> showAllContactsByScore(String uid) {
        String sql = "SELECT * FROM contacts where UID = ? order by score desc, contactName";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Contacts(
                        rs.getString("uid"),
                        rs.getString("contactUid"),
                        rs.getString("contactName"),
                        rs.getInt("score")
                ), uid);
    }
}
