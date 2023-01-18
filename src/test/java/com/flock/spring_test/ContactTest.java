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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactTest {
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
    public void givenContacts_whenGetAllContacts_thenListOfContacts() throws Exception {
        // given
        UserLoginCred user = new UserLoginCred("A","A","A");
        UserContact contact = new UserContact("A");
        userLoginRepo.addUser(user, contact);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/contact/showAll")
                .param("token", user.getToken()));

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
