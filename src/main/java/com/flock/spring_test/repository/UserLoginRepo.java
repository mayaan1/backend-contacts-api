package com.flock.spring_test.repository;

import com.flock.spring_test.mappers.UserLoginCredMapper;
import com.flock.spring_test.model.UserLoginCred;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.flock.spring_test.mappers.UserLoginCredMapper.USER_RM;

@Repository
public class UserLoginRepo {

    UserLoginCredMapper userLoginCredMapper;
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<UserLoginCred> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM UserLoginCred", USER_RM);
    }

    public UserLoginCred addUser(UserLoginCred user) {
        String sql = "Insert into UserLoginCred (username,password,token) Values(?,?,?)";
        try {
            user.setToken(user.generateToken());
            jdbcTemplate.update(sql, user.getUsername(),user.getPassword(), user.getToken());
        } catch (DuplicateKeyException ex){
            System.out.println("Duplicate Key!");
        }
        return user;
    }

    public UserLoginCred deleteUser(UserLoginCred user ) {
        String sql = "Delete from UserLoginCred where username=? and password=?";
        try {
            jdbcTemplate.update(sql, user.getUsername(),user.getPassword());
        } catch (Exception ex) {
            System.out.println("No user found");
        }
        return user;
    }

    public UserLoginCred getUserToken(UserLoginCred user) {
        String sql = "SELECT * FROM UserLoginCred Where username = ? and password = ?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql,new Object[]{user.getUsername(), user.getPassword()},USER_RM);
    }

    public String getPasswordForUsername(String username) {
        String sql = "SELECT * FROM UserLoginCred Where username = ?";
        UserLoginCred user = jdbcTemplate.queryForObject(sql,new Object[]{username},USER_RM);
        return user.getPassword();
    }
}
