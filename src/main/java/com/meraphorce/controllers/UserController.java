package com.meraphorce.controllers;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Controller Class for endpoints defintion
 * @author Juan Chavez
 * @since May/14/2024
 */
@RestController
@RequestMapping("/api/v1/users")
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
	public ResponseEntity<?> createUser(@RequestBody UserDTO userRequest) {
		ResponseEntity<?> response = null;
		User userData = mapper.toEntity(userRequest);
		User newUser = this.userService.createUser(userData);

		if(newUser != null) {
			response = ResponseEntity.ok(mapper.toDTO(newUser));
		} else {
			response = ResponseEntity.badRequest().body("Error to create User");
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
		ResponseEntity<?> response = null;
		List<UserDTO> listUsers = this.userService.getAllUsers();

		if(listUsers != null) {
			response = ResponseEntity.ok(listUsers);
		} else {
			response = ResponseEntity.badRequest().body("Error to get all Users");
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
		ResponseEntity<?> response = null;
		UserDTO userData = this.userService.getUserById(id);

		if(userData != null) {
			response = ResponseEntity.ok(userData);
		} else {
			response = ResponseEntity.badRequest().body("Error to get User[" + id + "]");
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
	public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
		ResponseEntity<?> response = null;
		UserDTO updateUserData = this.userService.updateUser(id, user);

		if(updateUserData != null) {
			response = ResponseEntity.ok(updateUserData);
		} else {
			response = ResponseEntity.badRequest().body("Error to update User[" + id + "]");
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
	public ResponseEntity<?> createUsersBulk(@RequestBody List<UserDTO> batchUsers) {
		ResponseEntity<?> response = null;

		if(!batchUsers.isEmpty()) {
			BulkResultDTO dto = new BulkResultDTO();
			User userData = null;
			User newUser = null;
			List<User> created = new ArrayList<User>();
			List<User> notCreated = new ArrayList<User>();

			for(UserDTO element: batchUsers) {
				userData = mapper.toEntity(element);
				newUser = this.userService.createUser(userData);

				if(newUser != null) {
					created.add(newUser); 
				} else { 
					notCreated.add(newUser); 
				};
			}
			
			dto.setSuccess(created);
			dto.setFailed(notCreated);
			
			response = ResponseEntity.ok(dto);
		} else {
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
