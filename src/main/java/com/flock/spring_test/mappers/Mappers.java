package com.flock.spring_test.mappers;

import com.flock.spring_test.model.Contacts;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import org.springframework.jdbc.core.RowMapper;
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

    public static MapSqlParameterSource getContactMap(Contacts contact) {
        return new MapSqlParameterSource("uid", contact.getUid())
                .addValue("contactUID", contact.getContactUID())
                .addValue("contactName", contact.getContactName())
                .addValue("score", contact.getScore());
    }

    public static MapSqlParameterSource getUserUsernameMap(String username){
        return new MapSqlParameterSource("username", username);
    }

    public static MapSqlParameterSource getUserTokenMap(String token){
        return new MapSqlParameterSource("token", token);
    }

    public static MapSqlParameterSource getUidMap(String uid){
        return new MapSqlParameterSource("uid", uid);
    }

    public static MapSqlParameterSource getUidLookUpMap(String uid, String lookUpText ){
        return new MapSqlParameterSource("uid", uid)
                .addValue("contactName", lookUpText + '%');
    }


    public static final RowMapper<UserContact> USER_CONTACT_RM = (rs, rowNum) ->
            new UserContact(
                    rs.getString("uid"),
                    rs.getString("mobile_no"),
                    rs.getString("address"),
                    rs.getString("email"),
                    rs.getString("name")
            );
    public static final RowMapper<UserLoginCred> USER_RM = (rs, rowNum) ->
            new UserLoginCred(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("token"));


    public static final RowMapper<Contacts> CONTACT_RM = (rs, rowNum) ->
            new Contacts(rs.getString("uid"),
                    rs.getString("contactUID"),
                    rs.getString("contactName"),
                    rs.getInt("score"));


}
