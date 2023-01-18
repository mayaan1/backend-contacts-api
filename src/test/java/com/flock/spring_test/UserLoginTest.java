package com.flock.spring_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.repository.UserLoginRepo;
import org.hamcrest.CoreMatchers;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginTest {
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
        String clearContacts = "DELETE FROM CONTACTS";
        jdbcTemplate.update(clearUserLogin);
        jdbcTemplate.update(clearContacts);
        jdbcTemplate.update(clearUsers);
        mapper = new ObjectMapper();
    }

    @Test
    public void givenUsers_whenGetAllUsers_thenListOfUsers() throws Exception {
        // given
        UserLoginCred user1 = new UserLoginCred("A","A","A");
        UserContact contact1 = new UserContact("A");
        userLoginRepo.addUser(user1, contact1);
        UserLoginCred user2 = new UserLoginCred("A1","A1","A1");
        UserContact contact2 = new UserContact("A1");
        userLoginRepo.addUser(user2, contact2);
        List<UserLoginCred> users = new ArrayList<>();
        users.add(user1); users.add(user2);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/auth/getAllUserCred"));
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(users));
    }


    @Test
    public void givenUsers_whenAddUser_thenUserAdded() throws Exception {
        // given
        int sizeOfDb = 0;
        UserLoginCred user = new UserLoginCred("A","A","A");

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
        assertEquals(getSizeOfDB(), sizeOfDb + 1);
    }

    @Test
    public void givenToken_whenGetToken_thenTokenFound() throws Exception {
        // given
        UserLoginCred user = new UserLoginCred("A","A","A");
        UserContact contact = new UserContact("A");
        userLoginRepo.addUser(user, contact);
        UserLoginCred reqBody = new UserLoginCred("A","A");

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/auth/getToken")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(reqBody))
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions res = mockMvc.perform(requestBuilder);
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(user));
    }


    @Test
    public void givenUsers_whenDeleteUser_thenUserDelete() throws Exception {
        // given
        UserLoginCred user1 = new UserLoginCred("A","A","A");
        UserContact contact1 = new UserContact("A");
        userLoginRepo.addUser(user1, contact1);
        UserLoginCred user2 = new UserLoginCred("A1","A1","A1");
        UserContact contact2 = new UserContact("A1");
        userLoginRepo.addUser(user2, contact2);
        int sizeOfDb = 2;
        UserLoginCred deleteUser = new UserLoginCred("A1", "A1");

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/delete")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(deleteUser))
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions res = mockMvc.perform(requestBuilder);

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(getSizeOfDB(), sizeOfDb - 1);
        assertEquals(isTokenInDB(deleteUser.getToken()), false);
        assertEquals(isTokenInDB(user1.getToken()), true);
    }

    private boolean isTokenInDB(String token) {
        return jdbcTemplate
                .queryForObject("SELECT COUNT(*) FROM USERLOGINCRED WHERE TOKEN = ?", new String[]{token}, Integer.class) > 0;
    }


    private Integer getSizeOfDB() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USERLOGINCRED", Integer.class);
    }
}
