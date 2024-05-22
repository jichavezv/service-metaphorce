package com.meraphorce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	private User userTest;
	
	@BeforeEach
	public void setUp() {
		userTest = service.createUser(new User().builder()
				.id("user1")
				.name("User One")
				.email("user1@web.com")
				.build());
	}
	
	@Test
	public void testGetUserById() {
		User data = service.getUserById(userTest.getId());
		
		assertNotNull(data);
		assertEquals(data.getId(), userTest.getId());
	}
	
	@Test
	public void testUpdateUser() {
		userTest.setEmail("new.email@web.com");
		userTest.setName("User Updated");
		
		User data = service.updateUser(userTest.getId(), userTest);
		
		assertNotNull(data);
		assertEquals(data.getName(), userTest.getName());
	}
	
	@Test
	public void testDeleteUser() {
		service.deleteUser(userTest.getId());
		
		User userDeleted = service.getUserById(userTest.getId());
		assertNull(userDeleted);
	}
	
	@Test
	public void testGetUsersName() {
		List<String> names = this.service.findAllUserNames();
		
		assertNotNull(names);
	}
}
