package com.test.repository;

import com.test.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Role entities. Extends the BaseRepository interface.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(String name);
}
