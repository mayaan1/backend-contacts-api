package com.flock.spring_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flock.spring_test.model.Contacts;
import com.flock.spring_test.model.UserContact;
import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.repository.ContactRepo;
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
public class ContactTest {
    @Autowired
    private UserLoginRepo userLoginRepo;

    @Autowired
    private ContactRepo contactRepo;

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
    public void givenContacts_whenGetAllContacts_thenListOfContacts() throws Exception {
        // given
        Contacts contact1 = new Contacts("A", "B", "Name1", 0);
        Contacts contact2 = new Contacts("A", "C", "Name2", 0);
        Contacts contact3 = new Contacts("B", "A", "NewName", 0);
        contactRepo.addContact(contact1);
        contactRepo.addContact(contact2);
        contactRepo.addContact(contact3);
        List<Contacts> contactsOfA = new ArrayList<>();
        contactsOfA.add(contact1);contactsOfA.add(contact2);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/contact/showAll")
                .param("token", "token1"));
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(contactsOfA));
    }

    @Test
    public void givenContacts_whenGetAllContactsByScore_thenListOfContacts() throws Exception {
        // given
        Contacts contact1 = new Contacts("A", "B", "Name1", 2);
        Contacts contact2 = new Contacts("A", "C", "Name2", 4);
        Contacts contact3 = new Contacts("B", "A", "NewName", 1);
        contactRepo.addContact(contact1);
        contactRepo.addContact(contact2);
        contactRepo.addContact(contact3);
        List<Contacts> contactsOfA = new ArrayList<>();
        contactsOfA.add(contact2);contactsOfA.add(contact1);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/contact/showAllContactsByScore")
                .param("token", "token1"));
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(contactsOfA));
    }

    @Test
    public void givenContacts_whenGetSingleContact_thenContact() throws Exception {
        // given
        Contacts contact1 = new Contacts("A", "B", "Name1", 2);
        Contacts contact2 = new Contacts("A", "C", "Name2", 4);
        Contacts contact3 = new Contacts("B", "A", "NewName", 1);
        contactRepo.addContact(contact1);
        contactRepo.addContact(contact2);
        contactRepo.addContact(contact3);
        Contacts resultContact = contact1;

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.get("/contact/viewSingleContact")
                .param("token", "token1")
                .param("contactUID", "B"));
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(4)));
        // result contact score updated
        resultContact.setScore(resultContact.getScore() + 1);
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(resultContact));
    }


    @Test
    public void givenContacts_whenAddContact_thenContactAdded() throws Exception {
        // given
        Contacts contact1 = new Contacts("A", "B", "Name1", 0);
        Contacts contact2 = new Contacts("B", "A", "NewName", 0);
        contactRepo.addContact(contact1);
        contactRepo.addContact(contact2);
        Integer sizeofDB = 2;
        Contacts contactToAdd = new Contacts("A", "C", "Name2", 0);

        // when
        ResultActions res = mockMvc.perform(MockMvcRequestBuilders.post("/contact/add")
                .param("token", "token1")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(contactToAdd))
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult result = res.andReturn();

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals(result.getResponse().getContentAsString(), mapper.writeValueAsString(contactToAdd));
        assertEquals(sizeofDB + 1, getSizeOfDB());
    }

    private Integer getSizeOfDB() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CONTACTS", Integer.class);
    }
}
