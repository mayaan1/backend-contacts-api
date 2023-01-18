package com.flock.spring_test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.service.UserLoginService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
class IntTest {
    private static MockMvc mockMvc;

    @Autowired
    WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String baseURL = "http://localhost:"+port;

    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init() {
        mockMvc = MockMvcBuilders.standaloneSetup().build();
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        mapper = new ObjectMapper();
        baseURL = baseURL + "/auth/add";
    }

    @Test
    void testInsertion() throws Exception {

        UserLoginCred user = new UserLoginCred("A", "A", "A");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/add")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String json = result.getResponse().getContentAsString();
//        UserLoginCred userReturned = mapper.readValue(json, UserLoginCred.class);

//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) content().contentType("application/json;charset=UTF-8"));

//        Mockito
//                .when(restTemplate.postForEntity(
//                        baseURL, UserLoginCred.class))
//          .thenReturn(new ResponseEntity(user, HttpStatus.OK));

//        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseURL)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(user))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        String json = result.getResponse().getContentAsString();

        assertEquals(json, "1");
    }
}
