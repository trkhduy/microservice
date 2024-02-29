package com.test.service.impl;

import com.test.dto.request.PermissionRequest;
import com.test.dto.response.PermissionResponse;
import com.test.entity.Permission;
import com.test.repository.PermissionRepository;
import com.test.service.PermissionService;
import com.test.utils.MapperUtils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;

    @Override
    @Transactional
    public PermissionResponse create(PermissionRequest request) {
        this.checkPermissionIfExist(request.getName());
        Permission permission = new Permission(
                request.getName(),
                request.getUri(),
                request.getMethod()
        );
        Permission newPer = repository.save(permission);
        return new PermissionResponse(
                newPer.getId(),
                newPer.getName(),
                newPer.getUri(),
                newPer.getMethod()
        );
    }

    @Override
    @Transactional
    public PermissionResponse update(Long id, PermissionRequest request) {
        Permission permission = this.checkPermissionExist(id);
        permission.setName(request.getName());
        permission.setUri(request.getUri());
        repository.save(permission);
        return new PermissionResponse(
                permission.getId(),
                permission.getName(),
                permission.getUri(),
                permission.getMethod()
        );
    }

    @Override
    public List<PermissionResponse> viewAll() {
        List<Permission> list = repository.findAll();
        return MapperUtils.toDTOs(list, PermissionResponse.class);
    }

    @Override
    public List<Permission> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Long id) {
        Permission permission = this.checkPermissionExist(id);
        repository.delete(permission);
    }

    private Permission checkPermissionExist(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Permission with ID " + id + " not found"));
    }

    private void checkPermissionIfExist(String name) {
        Optional<Permission> optionalPer = repository.findPermissionByName(name);
        if (optionalPer.isPresent()) {
            throw new IllegalArgumentException("Permission with name '" + name + "' already exists");
        }
    }
}
