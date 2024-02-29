package com.test.repository;

import com.test.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Permission entities. Extends the BaseRepository interface.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> findPermissionByName(String name);
    Optional<Permission> findPermissionByUri(String uri);
    Optional<List<Permission>> findPermissionByMethod(String method);
}
