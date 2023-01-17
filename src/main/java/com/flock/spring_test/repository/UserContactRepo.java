package com.flock.spring_test.repository;

import com.flock.spring_test.mappers.UserLoginCredMapper;
import com.flock.spring_test.model.UserContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.flock.spring_test.mappers.Mappers.getUserContactMap;
import static com.flock.spring_test.mappers.UserContactMapper.USER_CONTACT_RM;

@Repository
public class UserContactRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserContact deleteUserID(UserContact user) {
        String sql = "Delete from users where uid=:uid";
        try {
            namedParameterJdbcTemplate.update(sql, getUserContactMap(user));
        } catch (Exception ex) {
            System.out.println("No user found");
        }
        return user;
    }

    public UserContact addUser(UserContact user) {
        String sql = "Insert into Users (uid,mobileNo,address,email,name) Values(:uid,:mobileNo,:address,:email,:name)";
        try {
            namedParameterJdbcTemplate.update(sql, getUserContactMap(user));
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return user;
    }

    public List<UserContact> getAllUsers() {
        return namedParameterJdbcTemplate.query("SELECT * FROM users", USER_CONTACT_RM);
    }

    public void updateUserAddress(UserContact user) {
        String sql = "Update USERS set Address = :address Where uid = :uid";
        namedParameterJdbcTemplate.update(sql, getUserContactMap(user));
    }

    public void updateUserMobileNo(UserContact user) {
        String sql = "Update USERS set MobileNo = :mobileNo Where uid = :uid";
        namedParameterJdbcTemplate.update(sql, getUserContactMap(user));
    }

    public void updateUserName(UserContact user) {
        String sql = "Update USERS set Name = :name Where uid = :uid";
        namedParameterJdbcTemplate.update(sql, getUserContactMap(user));
    }

    public void updateUserEmail(UserContact user) {
        String sql = "Update USERS set email = :email Where uid = :uid";
        namedParameterJdbcTemplate.update(sql, getUserContactMap(user));
    }
}
