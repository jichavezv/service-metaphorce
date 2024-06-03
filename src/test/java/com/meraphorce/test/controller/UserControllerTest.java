package com.meraphorce.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import com.meraphorce.controllers.UserController;
import com.meraphorce.dto.BulkResultDTO;
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
		userTest = service.createUser(User.builder()
				.name("UserOne")
				.email("user1@web.com")
				.password("123456")
				.roles("admin,oper")
				.build());
	}

	@Test
	@WithMockUser
	public void testGetUserById() {
		ResponseEntity<UserDTO> data = controller.getUser(userTest.getId());
		UserDTO dto = data.getBody();

		assertNotNull(data);
		assertNotNull(dto);
		assertEquals(dto.getUserId(), this.userTest.getId());
	}

	@Test
	@WithMockUser
	public void testUpdateUser() {
		userTest.setName("User Updated");
		userTest.setEmail("new.email@web.com");

		ResponseEntity<UserDTO> response = controller.updateUser(this.userTest.getId(), this.mapper.toDTO(userTest));
		UserDTO dto = response.getBody();

		assertNotNull(response);
		assertNotNull(dto);
		assertEquals(dto.getUserName(), this.userTest.getName());
	}

	@Test
	@WithMockUser
	public void testDeleteUser() {
		ResponseEntity<Void> response = controller.deleteUser(userTest.getId());

		Optional<User> userDeleted = service.getUserById(userTest.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		assertFalse(userDeleted.isPresent());
	}


	@Test
	@WithMockUser
	public void testBulkUsers() {
		List<UserDTO> list = new ArrayList<UserDTO>();
		UserDTO bulkUser = null;

		for(int i=0; i<11; i++) {
			bulkUser = UserDTO.builder()
					.userName("User " + i)
					.userEmail("user_" + i + "@web.com")
					.password("123456")
					.build();

			list.add(bulkUser);
		}

		ResponseEntity<BulkResultDTO<UserDTO>> response = controller.createUsersBulk(list);
		BulkResultDTO<UserDTO> dto = response.getBody();

		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(dto);
	}
	
	@Test
	@WithMockUser
	public void testGetUserNames() {
		ResponseEntity<List<String>> response = controller.getUsersName();
		List<String> dto = response.getBody();
		
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(dto);
	}
	
	@Test
	@WithMockUser
	public void testFailedGetUserById() {
		ResponseEntity<UserDTO> data = controller.getUser("user1");
		UserDTO dto = data.getBody();
		
		assertNull(dto);
	}

}
