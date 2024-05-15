package com.meraphorce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

@SpringBootTest
public class UserServiceTest {
	
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
		User data = null;
		
		data = service.getUserById("user1");
		
		assertNotNull(data);
		assertEquals(data, userTest);
	}
	
	@Test
	public void testUpdateUser() {
		userTest.setEmail("new.email@web.com");
		userTest.setName("User Updated");
		
		User data = service.updateUser(userTest);
		
		assertNotNull(data);
		assertEquals(data.getName(), userTest.getName());
	}
	
	@Test
	public void testDeleteUser() {
		boolean isDeleted = service.deleteUser(userTest.getId());
		
		assertTrue(isDeleted);
	}
}
