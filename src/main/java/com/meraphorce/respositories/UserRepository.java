package com.meraphorce.respositories;

import com.meraphorce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>{

    public Optional<User> findByName(String name);
}
