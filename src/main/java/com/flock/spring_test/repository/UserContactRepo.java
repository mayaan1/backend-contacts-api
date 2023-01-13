package com.flock.spring_test.repository;

import com.flock.spring_test.mappers.UserLoginCredMapper;
import com.flock.spring_test.model.UserContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserContactRepo {

    UserLoginCredMapper userLoginCredMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserContact deleteUserID(UserContact user) {
        String sql = "Delete from users where uid=?";
        try {
            jdbcTemplate.update(sql, user.getUid());
        } catch (Exception ex) {
            System.out.println("No user found");
        }
        return user;
    }

    public UserContact addUser(UserContact user) {
        String sql = "Insert into Users (uid,mobile_no,address,email,name) Values(?,?,?,?,?)";
        try {
            jdbcTemplate.update(sql, user.getUid(),user.getMobile_no(), user.getAddress(), user.getEmail(), user.getName());
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return user;
    }

    public List<UserContact> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", (rs, rowNum) ->
                new UserContact(
                        rs.getString("uid"),
                        rs.getString("mobile_no"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("name")
                ));
    }
}
