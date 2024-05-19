package com.meraphorce.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.meraphorce.controllers.UserController;
import com.meraphorce.dto.BulkResultDTO;
import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
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

		service.createUser(userTest);
	}

	@Test
	public void testGetUserById() {
		ResponseEntity<?> data = controller.getUser(userTest.getId());
		Object dto = data.getBody();

		assertNotNull(data);
		assertNotNull(dto);
		assertInstanceOf(UserDTO.class, dto);
		assertEquals(((UserDTO) dto).getUserId(), this.userTest.getId());
	}

	@Test
	public void testUpdateUser() {
		userTest.setName("User Updated");
		userTest.setEmail("new.email@web.com");

		ResponseEntity<?> response = controller.updateUser(this.userTest.getId(), this.mapper.toDTO(userTest));
		Object dto = response.getBody();

		assertNotNull(response);
		assertNotNull(dto);
		assertInstanceOf(UserDTO.class, dto);
		assertEquals(((UserDTO) dto).getUserName(), this.userTest.getName());
	}

	@Test
	public void testDeleteUser() {
		ResponseEntity<?> response = controller.deleteUser(userTest.getId());

		UserDTO userDeleted = service.getUserById(userTest.getId());
		assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		assertNull(userDeleted);
	}


	@Test
	public void testBulkUsers() {
		List<UserDTO> list = new ArrayList<UserDTO>();
		UserDTO bulkUser = null;

		for(int i=0; i<11; i++) {
			bulkUser = new UserDTO().builder()
					.userId("user" + i)
					.userName("User One")
					.userEmail("user1@web.com")
					.build();

			list.add(bulkUser);
		}

		ResponseEntity<?> response = controller.createUsersBulk(list);
		Object dto = response.getBody();
		log.info("El DTO: " + dto);

		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(dto);
		assertInstanceOf(BulkResultDTO.class, dto);
	}

}
