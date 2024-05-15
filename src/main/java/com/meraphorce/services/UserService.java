package com.meraphorce.services;

import com.meraphorce.models.User;
import com.meraphorce.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public  List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    public User getUserById(String idValue) {
    	return this.userRepository.findById(idValue).get();
    }
    
    public User updateUser(User userUpdated) {
    	return this.userRepository.saveAndFlush(userUpdated);
    }
    
    public boolean deleteUser(String idValue) {
    	boolean flag = false;
    	User userDelete = getUserById(idValue);
    	
    	if(userDelete != null) {
    		this.userRepository.delete(userDelete);
    		flag = true;
    	}
    	
    	return flag;
    }
}
