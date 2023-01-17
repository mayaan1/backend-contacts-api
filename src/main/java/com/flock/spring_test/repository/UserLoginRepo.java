package com.flock.spring_test.repository;

import com.flock.spring_test.mappers.UserLoginCredMapper;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.flock.spring_test.mappers.Mappers.*;
import static com.flock.spring_test.mappers.UserLoginCredMapper.USER_RM;
import static com.flock.spring_test.repository.sqlConstants.UserLoginCred.*;

@Repository
public class UserLoginRepo {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<UserLoginCred> getAllUsers() {
        return namedParameterJdbcTemplate.query(SELECT_USERLOGIN, USER_RM);
    }

    public UserLoginCred addUser(UserLoginCred user, UserContact userContact) {
        try {
            namedParameterJdbcTemplate.update(INSERT_INTO_USERLOGIN, getUserLoginCredMap(user));
            namedParameterJdbcTemplate.update(INSERT_USERLOGIN_UID, getUserContactMap(userContact));
        } catch (Exception ex) {
            System.out.println("Duplicate Key!");
        }
        return user;
    }

    public UserLoginCred deleteUser(UserLoginCred user ) {
        try {
            namedParameterJdbcTemplate.update(DELETE_USERLOGIN_USERNAME_PASSWORD, getUserLoginCredMap(user));
        } catch (Exception ex) {
            System.out.println("No user found");
        }
        return user;
    }

    public UserLoginCred getUserToken(UserLoginCred user) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_USERLOGIN_USERNAME_PASSWORD, getUserLoginCredMap(user), USER_RM);
    }

    public String getPasswordForUsername(String username) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_USERLOGIN_USERNAME, getUserUsernameMap(username), USER_RM).getPassword();
    }

    public boolean isTokenPresent(String token) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_COUNT_USERLOGIN_TOKEN, getUserTokenMap(token), Integer.class) == 1;
    }

    public String getUsernameFromToken(String token) {
        return namedParameterJdbcTemplate.queryForObject(SELECT_USERLOGIN_TOKEN,getUserTokenMap(token),USER_RM).getUsername();
    }
}
