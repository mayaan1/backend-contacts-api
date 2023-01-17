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
import static com.flock.spring_test.repository.sqlConstants.UserContact.*;

@Repository
public class UserContactRepo {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserContact deleteUserID(UserContact user) {
        try {
            namedParameterJdbcTemplate.update(DELETE_USERS_UID, getUserContactMap(user));
        } catch (Exception ex) {
            System.out.println("No user found");
        }
        return user;
    }

    public UserContact addUser(UserContact user) {
        try {
            namedParameterJdbcTemplate.update(INSERT_INTO_USERS, getUserContactMap(user));
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return user;
    }

    public List<UserContact> getAllUsers() {
        return namedParameterJdbcTemplate.query(SELECT_USERS, USER_CONTACT_RM);
    }

    public void updateUserAddress(UserContact user) {
        namedParameterJdbcTemplate.update(UPDATE_USERS_ADDRESS_UID, getUserContactMap(user));
    }

    public void updateUserMobileNo(UserContact user) {
        namedParameterJdbcTemplate.update(UPDATE_USERS_MOBILENO_UID, getUserContactMap(user));
    }

    public void updateUserName(UserContact user) {
        namedParameterJdbcTemplate.update(UPDATE_USERS_NAME_UID, getUserContactMap(user));
    }

    public void updateUserEmail(UserContact user) {
        namedParameterJdbcTemplate.update(UPDATE_USERS_EMAIL_UID, getUserContactMap(user));
    }
}
