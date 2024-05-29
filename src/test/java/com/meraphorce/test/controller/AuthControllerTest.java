package com.meraphorce.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.meraphorce.controllers.AuthenticationController;
import com.meraphorce.dto.UserDTO;
import com.meraphorce.dto.auth.LoginResponseDTO;
import com.meraphorce.dto.auth.LoginUserDTO;
import com.meraphorce.dto.auth.UserAuthDTO;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class AuthControllerTest {
	@Autowired
	private AuthenticationController controller;
	
	@Autowired
	private UserService userService;
		
	@BeforeEach
	public void setUp() {
		userService.createUser(User.builder()
				.name("UserOne")
				.email("user1@web.com")
				.password("123456")
				.roles("admin,oper")
				.build());
	}
	
	@Test
	public void testWelcome() {
		String response = controller.welcome();
		
		assertNotNull(response);
		assertEquals(response, "This endpoint is not secure yet");
	}
	
	@Test
	public void testSignUp() {
		UserAuthDTO newUser = UserAuthDTO.builder()
				.name("NewUser")
				.email("new-user@web.com")
				.password("123456")
				.authorities(Arrays.stream("admin,oper".split(","))
		                .map(SimpleGrantedAuthority::new)
		                .collect(Collectors.toList()))
				.build();
		
		ResponseEntity<UserDTO> userSignedUp = controller.register(newUser);
		UserDTO dto = userSignedUp.getBody();
		
		assertNotNull(dto);
		assertEquals(dto.getUserName(), newUser.getName());
	}
	
	@Test
	public void testLogin() {
		ResponseEntity<LoginResponseDTO> response = controller.authenticate(LoginUserDTO.builder()
				.name("UserOne")
				.password("123456")
				.build());
		
		LoginResponseDTO loggedUser = response.getBody();
		log.info("Logged User: " + loggedUser);
		
		assertNotNull(loggedUser);
		assertNotNull(loggedUser.getToken());
		assertNotNull(loggedUser.getExpiration());
	}
}
