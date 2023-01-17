package com.flock.spring_test;

import com.flock.spring_test.model.UserLoginCred;
import com.flock.spring_test.repository.UserLoginRepo;
import com.flock.spring_test.service.UserLoginService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpringTestApplicationTests {
	@Autowired
	private UserLoginService userLoginService;
	@MockBean
	private UserLoginRepo userLoginRepo;

	@Test
	public void getAllUsersTest() {
		List<UserLoginCred> list = new ArrayList<>();
		list.add(new UserLoginCred("a","a","a"));
		list.add(new UserLoginCred("b","b","b"));

		when(userLoginRepo.getAllUsers()).thenReturn(list);

		List<UserLoginCred> users = userLoginService.getAllUsers();

		assertEquals(2, users.size());
	}

	@Test
	void contextLoads() {
	}

}
