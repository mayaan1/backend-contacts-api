package com.flock.spring_test.mappers;

import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class Mappers {
        public static MapSqlParameterSource getUserLoginCredMap(UserLoginCred user) {
            return new MapSqlParameterSource("username", user.getUsername())
                    .addValue("password", user.getPassword())
                    .addValue("token", user.getToken());
        }
        public static MapSqlParameterSource getUserContactMap(UserContact user) {
            return new MapSqlParameterSource("uid", user.getUid())
                    .addValue("address", user.getAddress())
                    .addValue("mobileNo", user.getMobileNo())
                    .addValue("email", user.getEmail())
                    .addValue("name",user.getName());
        }

    public static MapSqlParameterSource getUserUsernameMap(String username){
        return new MapSqlParameterSource("username", username);
    }

    public static MapSqlParameterSource getUserTokenMap(String token){
        return new MapSqlParameterSource("token", token);
    }

}
