package com.meraphorce.respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.meraphorce.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT u.name FROM User u")
	public List<String> getUserNames();
	
	public Optional<User> findByName(String name);
}
