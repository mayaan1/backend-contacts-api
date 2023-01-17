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

@Repository
public class UserLoginRepo {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<UserLoginCred> getAllUsers() {
        return namedParameterJdbcTemplate.query("SELECT * FROM UserLoginCred", USER_RM);
    }

    public UserLoginCred addUser(UserLoginCred user, UserContact userContact) {
        String sql = "Insert into UserLoginCred (username,password,token) Values(:username,:password,:token)";
        try {
            namedParameterJdbcTemplate.update(sql, getUserLoginCredMap(user));
            String userCreate = "Insert into Users (uid) Values(:uid)";
            namedParameterJdbcTemplate.update(userCreate, getUserContactMap(userContact));
        } catch (Exception ex) {
            System.out.println("Duplicate Key!");
        }
        return user;
    }

    public UserLoginCred deleteUser(UserLoginCred user ) {
        String sql = "Delete from UserLoginCred where username=:username and password=:password";
        try {
            namedParameterJdbcTemplate.update(sql, getUserLoginCredMap(user));
        } catch (Exception ex) {
            System.out.println("No user found");
        }
        return user;
    }

    public UserLoginCred getUserToken(UserLoginCred user) {
        String sql = "SELECT * FROM UserLoginCred Where username = :username and password = :password";
        return namedParameterJdbcTemplate.queryForObject(sql, getUserLoginCredMap(user), USER_RM);
    }

    public String getPasswordForUsername(String username) {
        String sql = "SELECT * FROM UserLoginCred Where username = :username";
        return namedParameterJdbcTemplate.queryForObject(sql, getUserUsernameMap(username), USER_RM).getPassword();
    }

    public boolean isTokenPresent(String token) {
        String sql = "SELECT count(*) FROM UserLoginCred WHERE token = :token";
        return namedParameterJdbcTemplate.queryForObject(sql, getUserTokenMap(token), Integer.class) == 1;
    }

    public String getUsernameFromToken(String token) {
        String sql = "SELECT * FROM UserLoginCred Where token = :token";
        return namedParameterJdbcTemplate.queryForObject(sql,getUserTokenMap(token),USER_RM).getUsername();
    }
}
