package com.flock.spring_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.repository.UserLoginRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AddTest {
    @Autowired
    private UserLoginRepo userLoginRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        String clearUserLogin = "DELETE FROM USERLOGINCRED";
        String clearUsers = "DELETE FROM USERS";
        jdbcTemplate.update(clearUserLogin);
        jdbcTemplate.update(clearUsers);
        mapper = new ObjectMapper();
    }

    @Test
    public void givenUsers_whenGetAllUsers_thenListOfUsers() throws Exception {
        // given
        UserLoginCred users = new UserLoginCred("A","A","A");
        UserContact contact = new UserContact("A");
        userLoginRepo.addUser(users, contact);
        UserLoginCred users1 = new UserLoginCred("A1","A1","A1");
        UserContact contact1 = new UserContact("A1");
        userLoginRepo.addUser(users1, contact1);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/auth/getAllUserCred"));

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));

    }


    @Test
    public void givenUsers_whenAddUser_thenUserAdded() throws Exception {
        // given
        int sizeOfDb = 0;
        UserLoginCred user = new UserLoginCred("A","A","A");
        UserContact contact = new UserContact("A");

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions res = mockMvc.perform(requestBuilder);

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(user.getUsername())));
        res.andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(user.getPassword())));
    }
}
