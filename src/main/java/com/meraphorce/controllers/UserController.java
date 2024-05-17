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

import com.meraphorce.dto.UserDTO;
import com.meraphorce.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
    	ResponseEntity<?> response = null;
    	UserDTO newUserData = this.userService.createUser(user);

    	if(newUserData != null) {
    		response = ResponseEntity.ok(newUserData);
    	} else {
    		response = ResponseEntity.badRequest().body("Error to create User");
    	}
    	
        return response;
    }

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
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
    	return ResponseEntity.ok(this.userService.deleteUser(id));
    }
}
