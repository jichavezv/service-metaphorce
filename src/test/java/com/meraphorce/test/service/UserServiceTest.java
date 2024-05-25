package com.meraphorce.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
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
		Optional<User> data = service.getUserById(userTest.getId());

		assertNotNull(data.get());
		assertEquals(data.get().getId(), userTest.getId());
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

		Optional<User> userDeleted = service.getUserById(userTest.getId());
		log.debug("User Delete: " + userDeleted);
		assertTrue(userDeleted.isEmpty());
	}

	@Test
	public void testGetUsersName() {
		List<String> names = this.service.findAllUserNames();

		assertNotNull(names);
	}

	@Test
	public void testFailedGetUserById() {
		Optional<User> data = service.getUserById("user1");
		assertFalse(data.isPresent());
	}
}
