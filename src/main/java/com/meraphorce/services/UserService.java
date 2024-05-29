package com.meraphorce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    
    @Autowired
    private PasswordEncoder encoder;

    /**
     * Create a user 
     * @param user Entity data.
     * @return User data inserted.
     * @author Juan Chavez
     * @since May/14/2024
     */
    public User createUser(User user) {
    	User data = null;
    	
    	try {
    		user.setPassword(encoder.encode(user.getPassword()));
			data = this.userRepository.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to create user --> " + e);
		}
    	
        return data;
    }

    /**
     * Find all users from Database.
     * @return List of users
     * @author Juan Chavez
     * @since May/14/2024
     */
    public List<User> getAllUsers() {
    	List<User> data = null;
    	
    	try {
			data = this.userRepository.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to get all users --> " + e);
		}
    	
        return data;
    }
    
    /**
     * Find a User by Id
     * @param idValue User Id to find
     * @return User Data
     * @author Juan Chavez
     * @since May/14/2024
     */
    public Optional<User> getUserById(String idValue) {
    	Optional<User> data = null;
    	
    	try {
			data = this.userRepository.findById(idValue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to get user [" + idValue + "] --> " + e);
		}
    	
    	return data;
    }
    
    /**
     * Modify a User
     * @param idValue User Id
     * @param userUpdated User data to update
     * @return User Data
     * @author Juan Chavez
     * @since May/14/2024
     */
    public User updateUser(String idValue, User userUpdated) {
    	User userSaved = null;
    	
    	try {
			userUpdated.setId(idValue);
			
			userSaved = this.userRepository.saveAndFlush(userUpdated);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.warn("Error to update user [" + idValue + "] --> " + e);
		}
    	
    	return userSaved;
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
    
    /**
     * Get all Users name in one transaction
     * @return List of names
     * @author Juan Chavez
     * @since May/18/2024
     */
    public List<String> findAllUserNames() {
    	return this.userRepository.getUserNames();
    }
}
