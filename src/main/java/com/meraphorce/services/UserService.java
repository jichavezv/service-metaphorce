package com.meraphorce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service Layer to handle User Data
 * @author Juan Chavez
 * @since May/14/2024
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private static UserMapper mapper = new UserMapper();

    /**
     * Create a user from a DTO 
     * @param user DTO with user data.
     * @return User data inserted.
     * @author Juan Chavez
     * @since May/14/2024
     */
    public UserDTO createUser(UserDTO user) {
    	UserDTO newUser = null;
    	User data = null;
    	
    	
    	try {
			data = this.userRepository.save(mapper.toEntity(user));
			newUser = mapper.toDTO(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to create user --> " + e);
		}
    	
        return newUser;
    }

    /**
     * Find all users from Database.
     * @return List of users
     * @author Juan Chavez
     * @since May/14/2024
     */
    public List<UserDTO> getAllUsers() {
    	List<UserDTO> list = null;
    	List<User> data = null;
    	
    	try {
			data = this.userRepository.findAll();
			
			if(data != null && !data.isEmpty()) {
				list = data.stream().map(mapper::toDTO).toList();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to get all users --> " + e);
		}
    	
        return list;
    }
    
    /**
     * Find a User by Id
     * @param idValue User Id to find
     * @return User Data
     * @author Juan Chavez
     * @since May/14/2024
     */
    public UserDTO getUserById(String idValue) {
    	UserDTO user = null;
    	User data = null;
    	
    	try {
			data = this.userRepository.findById(idValue).get();
			
			if(data != null) {
				user = mapper.toDTO(data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to get user [" + idValue + "] --> " + e);
		}
    	
    	return user;
    }
    
    /**
     * Modify a User
     * @param idValue User Id
     * @param userUpdated User data to update
     * @return User Data
     * @author Juan Chavez
     * @since May/14/2024
     */
    public UserDTO updateUser(String idValue, UserDTO userUpdated) {
    	UserDTO user = null;
    	User data = null;
    	
    	try {
			userUpdated.setUserId(idValue);
			
			data = this.userRepository.saveAndFlush(mapper.toEntity(userUpdated));
			user = mapper.toDTO(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to update user [" + idValue + "] --> " + e);
		}
    	
    	return user;
    }
    
    /**
     * Delete a user from database
     * @param idValue User Id
     * @author Juan Chavez
     * @since May/14/2024
     */
    public void deleteUser(String idValue) {
    	User userDelete = null;
    	
    	try {
			userDelete = this.userRepository.findById(idValue).get();
			
			if(userDelete != null) {
				this.userRepository.delete(userDelete);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to delete user [" + idValue + "] --> " + e);
		}
    }
}
