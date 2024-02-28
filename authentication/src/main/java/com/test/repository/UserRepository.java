package com.test.repository;

import com.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing User entities. Extends the BaseRepository interface.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
