package com.meraphorce.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.impl.UserMapper;
import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private static UserMapper mapper = new UserMapper();
    
    Logger logger = LogManager.getLogger(UserService.class);

    public UserDTO createUser(UserDTO user) {
    	UserDTO newUser = null;
    	User data = null;
    	
    	
    	try {
			data = this.userRepository.save(mapper.toEntity(user));
			newUser = mapper.toDTO(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Error to create user --> " + e);
		}
    	
        return newUser;
    }

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
			logger.warn("Error to get all users --> " + e);
		}
    	
        return list;
    }
    
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
			logger.warn("Error to get user [" + idValue + "] --> " + e);
		}
    	
    	return user;
    }
    
    public UserDTO updateUser(String idValue, UserDTO userUpdated) {
    	UserDTO user = null;
    	User data = null;
    	
    	try {
			userUpdated.setUserId(idValue);
			
			data = this.userRepository.saveAndFlush(mapper.toEntity(userUpdated));
			user = mapper.toDTO(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Error to update user [" + idValue + "] --> " + e);
		}
    	
    	return user;
    }
    
    public void deleteUser(String idValue) {
    	User userDelete = null;
    	
    	try {
			userDelete = this.userRepository.findById(idValue).get();
			
			if(userDelete != null) {
				this.userRepository.delete(userDelete);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.warn("Error to delete user [" + idValue + "] --> " + e);
		}
    }
}
