package com.flock.spring_test.mappers;

import com.flock.spring_test.model.UserLoginCred;
import org.springframework.jdbc.core.RowMapper;

public class UserLoginCredMapper {
    public static final RowMapper<UserLoginCred> USER_RM = (rs, rowNum) ->
            new UserLoginCred(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("token"));
}
