package com.meraphorce.controllers;

import com.meraphorce.dtos.AuthRequest;
import com.meraphorce.dtos.UserRequest;
import com.meraphorce.dtos.UserResponse;
import com.meraphorce.models.User;
import com.meraphorce.services.JwtService;
import com.meraphorce.services.UserInfoService;
import com.meraphorce.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;


    /**
     * Creates a new user.
     *
     * @param userRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("Creating user: {}", userRequest);

        UserResponse createdUser = modelMapper.map(userService.createUser(modelMapper.map(userRequest, User.class)), UserResponse.class);
        return ResponseEntity.ok(createdUser);
    }


    /**
     * Fetches all users.
     *
     * @return List of users.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Fetching all users ");
        List<UserResponse> users = userService.getAllUsers().stream()
                .map(user -> UserResponse
                        .builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
        log.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Fetches a user by ID.
     *
     * @param id
     * @return User with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@NotBlank @PathVariable String id) {
        log.info("Fetching user with id: {}", id);

        return userService.getUserById(id)
                .map(user -> {
                    log.info("User found: {}", user);
                    return ResponseEntity.ok(modelMapper.map(user, UserResponse.class));
                })
                .orElseGet(() -> {
                    log.warn("User not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Updates a user.
     *
     * @param id
     * @param userRequest
     * @return Updated user.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@NotBlank @PathVariable String id, @Valid @RequestBody UserRequest userRequest) {
        log.info("Updating user with id: {}", id);
        UserResponse updatedUser = modelMapper.map(userService.updateUser(id, modelMapper.map(userRequest, User.class)), UserResponse.class);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@NotBlank @PathVariable String id) {
        log.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "This endpoint is not secure yet";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody User userInfo) {
        userInfo.setId(UUID.randomUUID().toString());
        return service.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
