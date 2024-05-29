package com.meraphorce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.meraphorce.models.User;
import com.meraphorce.services.AuthenticationService;
import com.meraphorce.services.UserService;

@SpringBootTest
public class AuthServiceTest {
	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private UserService userService;

	private User userTest;
	
	@BeforeEach
	public void setUp() {
		userTest = userService.createUser(User.builder()
				.name("UserOne")
				.email("user1@web.com")
				.password("123456")
				.roles("admin,oper")
				.build());
	}
	
	@Test
	public void testLoadUserByUsername() {
		UserDetails data = service.loadUserByUsername(userTest.getName());

		assertNotNull(data);
		assertEquals(data.getUsername(), userTest.getName());
	}
	
	@Test
	public void testFailLoadUserByUsername() {
		UserDetails data = service.loadUserByUsername("X");

		assertNull(data);
	}
	
	@Test
	public void testSignUp() {
		User newUser = User.builder()
				.name("NewUser")
				.email("new-user@web.com")
				.password("123456")
				.roles("admin,oper")
				.build();
		
		User userSignedUp = service.signUp(newUser);

		assertNotNull(userSignedUp);
		assertEquals(userSignedUp.getName(), newUser.getName());
	}

}
