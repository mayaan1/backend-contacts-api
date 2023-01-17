package com.flock.spring_test.repository;

public interface sqlConstants {
    interface Contacts {
        String INSERT_INTO_CONTACTS ="Insert into Contacts (uid,contactUID,contactName,score) Values(:uid,:contactUID,:contactName,:score)";
        String SELECT_CONTACT_UID = "SELECT * FROM contacts where uid = :uid";
        String SELECT_CONTACT_UID_CONTACTNAME_ORDER ="SELECT * FROM contacts where UID = :uid and contactName like :contactName order by score desc";
        String SELECT_CONTACT_UID_ORDER = "SELECT * FROM contacts where UID = :uid order by score desc";
        String SELECT_CONTACT_UID_CONTACTID = "SELECT * FROM contacts where UID = :uid and contactUID = :contactUID";
        String UPDATE_CONTACT_SCORE_UID_CONTACTID =  "Update contacts set score = score + 1 where UID = :uid and contactUID = :contactUID";
        String SELECT_CONTACT_UID_ORDER_SCORE_NAME = "SELECT * FROM contacts where UID = :uid order by score desc, contactName";
    }

    interface UserContact {
        String DELETE_USERS_UID = "Delete from users where uid=:uid";
        String INSERT_INTO_USERS = "Insert into Users (uid,mobileNo,address,email,name) Values(:uid,:mobileNo,:address,:email,:name)";
        String SELECT_USERS = "SELECT * FROM users";
        String UPDATE_USERS_ADDRESS_UID = "Update USERS set Address = :address Where uid = :uid";
        String UPDATE_USERS_MOBILENO_UID = "Update USERS set MobileNo = :mobileNo Where uid = :uid";
        String UPDATE_USERS_NAME_UID = "Update USERS set Name = :name Where uid = :uid";
        String UPDATE_USERS_EMAIL_UID = "Update USERS set email = :email Where uid = :uid";
    }

    interface UserLoginCred {
        String SELECT_USERLOGIN = "SELECT * FROM UserLoginCred";
        String INSERT_INTO_USERLOGIN = "Insert into UserLoginCred (username,password,token) Values(:username,:password,:token)";
        String INSERT_USERLOGIN_UID = "Insert into Users (uid) Values(:uid)";
        String DELETE_USERLOGIN_USERNAME_PASSWORD = "Delete from UserLoginCred where username=:username and password=:password";
        String SELECT_USERLOGIN_USERNAME_PASSWORD = "SELECT * FROM UserLoginCred Where username = :username and password = :password";
        String SELECT_USERLOGIN_USERNAME = "SELECT * FROM UserLoginCred Where username = :username";
        String SELECT_COUNT_USERLOGIN_TOKEN = "SELECT count(*) FROM UserLoginCred WHERE token = :token";
        String SELECT_USERLOGIN_TOKEN = "SELECT * FROM UserLoginCred Where token = :token";
    }
}
