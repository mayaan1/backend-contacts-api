package com.flock.spring_test.mappers;

import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import org.springframework.jdbc.core.RowMapper;

public class UserContactMapper {
    public static final RowMapper<UserContact> USER_CONTACT_RM = (rs, rowNum) ->
        new UserContact(
                rs.getString("uid"),
                rs.getString("mobileNo"),
                rs.getString("address"),
                rs.getString("email"),
                rs.getString("name")
        );
}
