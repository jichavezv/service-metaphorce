package com.meraphorce.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meraphorce.dto.BulkResultDTO;
import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.services.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller Class for endpoints defintion
 * @author Juan Chavez
 * @since May/14/2024
 */
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	private static UserMapper mapper = new UserMapper();

	/**
	 * Implements the HTTP Post to create a User
	 * @param user User Data
	 * @return New user create
	 * @author Juan Chavez
	 * @since May/15/2024 
	 */
	@PostMapping
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userRequest) {
		log.info("User to create: " + userRequest);
		ResponseEntity<?> response = null;
		User newUser = this.userService.createUser(mapper.toEntity(userRequest));

		if(newUser != null) {
			response = ResponseEntity.ok(mapper.toDTO(newUser));
			log.info("User created: " + newUser);
		} else {
			response = ResponseEntity.badRequest().body("Error to create User");
			log.info("Error to create User: " + userRequest);
		}

		return response;
	}

	/**
	 * Implements the HTTP Get to find all users
	 * @return List of Users
	 * @return New user create
	 * @author Juan Chavez
	 * @since May/15/2024
	 */
	@GetMapping
	public ResponseEntity<?> getAllUsers() {
		log.info("Get All users ...");
		ResponseEntity<?> response = null;
		List<User> listUsers = this.userService.getAllUsers();

		if(listUsers != null) {
			log.info("Users[" + listUsers.size() + "]");
			response = ResponseEntity.ok(listUsers.stream().map(mapper::toDTO).toList());
		} else {
			response = ResponseEntity.badRequest().body("Error to get all Users");
			log.info("Error to get all Users");
		}

		return response;
	}

	/**
	 * Implements HTTP Get to find a user by id
	 * @param id User Id
	 * @return User data
	 * @return New user create
	 * @author Juan Chavez
	 * @since May/15/2024
	 */
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable String id) {
		log.info("Get User[" + id + "]");
		ResponseEntity<?> response = null;
		User userData = this.userService.getUserById(id);

		if(userData != null) {
			response = ResponseEntity.ok(mapper.toDTO(userData));
			log.info("User --> " + userData);
		} else {
			response = ResponseEntity.badRequest().body("Error to get User[" + id + "]");
			log.info("User[" + id + "] not found");
		}

		return response; 
	}

	/**
	 * Implements HTTP Put for update a user
	 * @param id User Id
	 * @param user User data to update
	 * @return User modified
	 * @return New user create
	 * @author Juan Chavez
	 * @since May/15/2024
	 */
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody UserDTO user) {
		log.info("User[" + id + "] to update: " + user);
		ResponseEntity<?> response = null;
		User updateUserData = this.userService.updateUser(id, mapper.toEntity(user));

		if(updateUserData != null) {
			response = ResponseEntity.ok(mapper.toDTO(updateUserData));
			log.info("User[" + id + "] updated");
		} else {
			response = ResponseEntity.badRequest().body("Error to update User[" + id + "]");
			log.info("User[" + id + "] not updated");
		}

		return response;
	}

	/**
	 * Implements HTTP Delete for delete a user
	 * @param id User Id
	 * @return Response
	 * @return New user create
	 * @author Juan Chavez
	 * @since May/15/2024
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		log.info("User[" + id + "] to delete");
		this.userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Implements HTTP POST to create multiple users  
	 * @param batchUsers List of Users
	 * @return Results for the bulk process
	 * @author Juan Chavez
	 * @since May/18/2024
	 */
	@PostMapping("/bulk-import")
	public ResponseEntity<?> createUsersBulk(@Valid @RequestBody List<UserDTO> batchUsers) {
		ResponseEntity<?> response = null;

		if(!batchUsers.isEmpty()) {
			log.info("Create Users by Bulk: " + batchUsers.size());
			BulkResultDTO dto = new BulkResultDTO();
			User newUser = null;
			List<User> created = new ArrayList<User>();
			List<User> notCreated = new ArrayList<User>();

			for(UserDTO element: batchUsers) {
				newUser = this.userService.createUser(mapper.toEntity(element));

				if(newUser != null) {
					created.add(newUser); 
				} else { 
					notCreated.add(newUser); 
				};
			}
			
			dto.setSuccess(created);
			log.info("Users created: " + created.size());
			dto.setFailed(notCreated);
			log.info("Users failed: " + notCreated.size());
			
			response = ResponseEntity.ok(dto);
		} else {
			log.info("Invalid List of users");
			response = ResponseEntity.badRequest().body("Send a List of Users");
		}

		return response;
	}
	
	/**
	 * Implements HTTP Get to find the names of all users.
	 * @return List of names
	 * @author Juan Chavez
	 * @since May/18/2024
	 */
	@GetMapping("/names")
	public ResponseEntity<?> getUsersName() {
		ResponseEntity<?> response = null;
		List<String> names = this.userService.findAllUserNames();
		
		if(names != null && !names.isEmpty()) {
			response = ResponseEntity.ok(names);
		} else {
			response = ResponseEntity.noContent().build();
		}
		return response;
	}
}
