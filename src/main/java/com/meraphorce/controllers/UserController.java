package com.meraphorce.controllers;

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

import com.meraphorce.dto.ResponseDTO;
import com.meraphorce.dto.UserDTO;
import com.meraphorce.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO<?>> createUser(@RequestBody UserDTO user) {
    	ResponseEntity<ResponseDTO<?>> response = null;
    	ResponseDTO<?> responseDTO = null;
    	UserDTO newUserData = this.userService.createUser(user);
    	
    	if(newUserData != null) {
    		responseDTO = ResponseDTO.builder()
    				.object(newUserData)
    				.status(true)
    				.build();
    	} else {
    		responseDTO = ResponseDTO.builder()
    				.message("Error to create user.")
    				.status(false)
    				.build();
    	}
    	
    	response = ResponseEntity.ok(responseDTO);
    	
        return response;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getAllUsers() {
    	ResponseEntity<ResponseDTO<?>> response = null;
    	ResponseDTO<?> responseDTO = null;
    	List<UserDTO> listUsers = this.userService.getAllUsers();
    	
    	if(listUsers != null) {
    		responseDTO = ResponseDTO.builder()
    				.object(listUsers)
    				.status(true)
    				.build();
    	} else {
    		responseDTO = ResponseDTO.builder()
    				.message("Error to get all users.")
    				.status(false)
    				.build();
    	}
    	
    	response = ResponseEntity.ok(responseDTO);
    	
        return response;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> getUser(@PathVariable String id) {
    	ResponseEntity<ResponseDTO<?>> response = null;
    	ResponseDTO<?> responseDTO = null;
    	UserDTO userData = this.userService.getUserById(id);
    	
    	if(userData != null) {
    		responseDTO = ResponseDTO.builder()
    				.object(userData)
    				.status(true)
    				.build();
    	} else {
    		responseDTO = ResponseDTO.builder()
    				.message("Error to get User[" + id + "].")
    				.status(true)
    				.build();
    	}
    	
    	response = ResponseEntity.ok(responseDTO);
    	
        return response; 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
    	ResponseEntity<ResponseDTO<?>> response = null;
    	ResponseDTO<?> responseDTO = null;
    	UserDTO updateUserData = this.userService.updateUser(id, user);
    	
    	if(updateUserData != null) {
    		responseDTO = ResponseDTO.builder()
    				.object(updateUserData)
    				.status(true)
    				.build();
    	} else {
    		responseDTO = ResponseDTO.builder()
    				.message("Error to update User[" + id + "].")
    				.status(true)
    				.build();
    	}
    	
    	response = ResponseEntity.ok(responseDTO);
    	
    	return response;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> deleteUser(@PathVariable String id) {
    	ResponseEntity<ResponseDTO<?>> response = null;
    	ResponseDTO<?> responseDTO = null;
    	boolean  isDeleted = this.userService.deleteUser(id);
    	
    	responseDTO = ResponseDTO.builder()
				.message(isDeleted? "User deleted": "Error to delete User[" + id + "].")
				.status(isDeleted)
				.build();
    	
    	response = ResponseEntity.ok(responseDTO);
    	
    	return response;
    }
}
