package com.meraphorce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.meraphorce.models.User;
import com.meraphorce.services.AuthenticationService;
import com.meraphorce.services.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class AuthServiceTest {
	@Autowired
	private AuthenticationService service;
	
	@Autowired
	private UserService userService;

	private User userTest;
	
	@BeforeEach
	public void setUp() {
		userTest = userService.createUser(User.builder()
				.name("User" + new Random().nextInt(100))
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
		UserDetails data = null;
		
		try {
			data = service.loadUserByUsername("X");
		} catch (UsernameNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			log.info("Error --> " + e);
		}

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
