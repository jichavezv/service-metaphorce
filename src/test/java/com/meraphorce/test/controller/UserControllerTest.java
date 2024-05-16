package com.meraphorce.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.meraphorce.controllers.UserController;
import com.meraphorce.dto.ResponseDTO;
import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

@SpringBootTest
public class UserControllerTest {
	@Autowired
	private UserController controller;
	
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
		
		service.createUser(this.mapper.toDTO(userTest));
	}
	
	@Test
	public void testGetUserById() {
		ResponseEntity<ResponseDTO<?>> data = controller.getUser(userTest.getId());
		ResponseDTO<?> dto = data.getBody();
		
		assertNotNull(data);
		assertNotNull(dto);
		assertInstanceOf(UserDTO.class, dto.getObject());
		assertEquals(((UserDTO) dto.getObject()).getUserId(), this.userTest.getId());
	}
	
	@Test
	public void testUpdateUser() {
		userTest.setName("User Updated");
		userTest.setEmail("new.email@web.com");
		
		ResponseEntity<ResponseDTO<?>> response = controller.updateUser(this.userTest.getId(), this.mapper.toDTO(userTest));
		ResponseDTO<?> dto = response.getBody();
		
		assertNotNull(response);
		assertNotNull(dto);
		assertInstanceOf(UserDTO.class, dto.getObject());
		assertEquals(((UserDTO) dto.getObject()).getUserName(), this.userTest.getName());
	}
	
	@Test
	public void testDeleteUser() {
		ResponseEntity<ResponseDTO<?>> response = controller.deleteUser(userTest.getId());
		ResponseDTO<?> dto = response.getBody();
		
		assertNotNull(response);
		assertNotNull(dto);
		assertInstanceOf(Boolean.class, dto.isStatus());
		assertTrue(((boolean) dto.isStatus()));
	}

}
