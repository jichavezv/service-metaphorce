package com.meraphorce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	private User userTest;
	
	private UserMapper mapper = new UserMapper();
	
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
		UserDTO data = null;
		
		data = service.getUserById("user1");
		
		assertNotNull(data);
		assertEquals(data.getUserId(), userTest.getId());
	}
	
	@Test
	public void testUpdateUser() {
		userTest.setEmail("new.email@web.com");
		userTest.setName("User Updated");
		
		UserDTO data = service.updateUser(userTest.getId(), this.mapper.toDTO(userTest));
		
		assertNotNull(data);
		assertEquals(data.getUserName(), userTest.getName());
	}
	
	@Test
	public void testDeleteUser() {
		service.deleteUser(userTest.getId());
		
		UserDTO userDeleted = service.getUserById(userTest.getId());
		assertNull(userDeleted);
	}
}
