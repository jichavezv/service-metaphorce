package com.meraphorce.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meraphorce.dto.UserDTO;
import com.meraphorce.dto.auth.LoginResponseDTO;
import com.meraphorce.dto.auth.LoginUserDTO;
import com.meraphorce.dto.auth.UserAuthDTO;
import com.meraphorce.mapper.impl.UserAuthMapper;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.AuthenticationService;
import com.meraphorce.services.JwtService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api/v1/auth")
@RestController
@Slf4j
public class AuthenticationController {
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationService authenticationService;

	private UserAuthMapper mapper = new UserAuthMapper();
	
	private UserMapper userMapper = new UserMapper();
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@GetMapping("/welcome")
	public String welcome() {
		return "This endpoint is not secure yet";
	}

	@PostMapping("/signup")
	public ResponseEntity<UserDTO> register(@Valid @RequestBody UserAuthDTO registerUserDto) {
		log.info("User to register: " + registerUserDto);
		ResponseEntity<UserDTO> response = null;
		User registeredUser = authenticationService.signUp(mapper.toEntity(registerUserDto));
		
		if(registeredUser != null) {
			response = ResponseEntity.ok(userMapper.toDTO(registeredUser));
			log.info("User registered: " + registeredUser);
		} else {
			response = ResponseEntity.badRequest().build();
			log.info("Error to sign up User: " + registeredUser);
		}

		return response;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> authenticate(@Valid @RequestBody LoginUserDTO loginUserDto) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getName(), loginUserDto.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = jwtService.generateToken(loginUserDto.getName());
			long expiration = jwtService.extractExpiration(token).getTime();
			return ResponseEntity.ok(LoginResponseDTO.builder()
					.token(token)
					.expiration(expiration)
					.build());
		} else {
			throw new UsernameNotFoundException("Invalid user request!");
		}
	}


}
