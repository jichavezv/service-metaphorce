package com.meraphorce.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.meraphorce.controllers.UserController;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

@SpringBootTest
public class UserControllerTest {
	@Autowired
	private UserController controller;
	
	@Autowired
	private UserService service;
	
	private User userTest;
		
	@BeforeEach
	public void setUp() {
		userTest = new User().builder()
				.id("user1")
				.name("User One")
				.email("user1@web.com")
				.build();
		
		service.createUser(userTest);
	}
	
	@Test
	public void testGetUserById() {
		ResponseEntity<User> data = controller.getUser(userTest.getId());
		
		assertNotNull(data);
		assertEquals(data.getBody(), userTest);
	}
	
	@Test
	public void testUpdateUser() {
		userTest.setName("User Updated");
		userTest.setEmail("new.email@web.com");
		
		ResponseEntity<User> response = controller.updateUser(userTest);
		
		assertNotNull(response);
		assertEquals(response.getBody().getName(), "User Updated");
	}
	
	@Test
	public void testDeleteUser() {
		ResponseEntity<Boolean> response = controller.deleteUser(userTest.getId());
		
		assertNotNull(response);
		assertTrue(response.getBody());
	}

}
