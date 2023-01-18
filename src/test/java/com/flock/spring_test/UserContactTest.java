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
public class UserContactTest {
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
        // For auth, initial users
        UserLoginCred user1 = new UserLoginCred("A","A","token1");
        UserContact contact1 = new UserContact("A");
        UserLoginCred user2 = new UserLoginCred("B","B","token2");
        UserContact contact2 = new UserContact("B");
        UserLoginCred user3 = new UserLoginCred("C","C","token3");
        UserContact contact3 = new UserContact("C");
        userLoginRepo.addUser(user1, contact1);
        userLoginRepo.addUser(user2, contact2);
        userLoginRepo.addUser(user3, contact3);
    }

    @Test
    public void givenUsers_whenGetAllUsers_thenListOfUsers() throws Exception {
        // given
        UserContact user1 = new UserContact("A");
        UserContact user2 = new UserContact("B");
        UserContact user3 = new UserContact("C");
        List<UserContact> users = new ArrayList<>();
        users.add(user1); users.add(user2); users.add(user3);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/user/getAllUsers")
                .param("token", "token1"));
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(3)));
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(users));
    }

    @Test
    public void givenUsers_whenDeleteUser_thenUserDelete() throws Exception {
        // given
        int sizeOfDb = 3;
        UserContact deleteUser = new UserContact("A");

//        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/delete")
                .param("token", "token1")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(deleteUser))
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions res = mockMvc.perform(requestBuilder);
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(sizeOfDb - 1, getSizeOfUsers());
    }
    @Test
    public void givenUsers_whenUpdateUser_thenUserUpdated() throws Exception {
        // given
        UserContact updatedUser = new UserContact("A", "989898989898", "A/50", "a@gamil.com", "Name");
        UserContact user2 = new UserContact("B");
        UserContact user3 = new UserContact("C");
        List<UserContact> users = new ArrayList<>();
        users.add(updatedUser); users.add(user2); users.add(user3);
        Integer sizeOfDb = 3;
//        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .param("token", "token1")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updatedUser))
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions res = mockMvc.perform(requestBuilder);
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(sizeOfDb, getSizeOfUsers());
    }
    private Integer getSizeOfUsers() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM USERS", Integer.class);
    }
}
