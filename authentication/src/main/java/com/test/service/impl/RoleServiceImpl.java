package com.test.service.impl;

import com.test.dto.request.RoleRequest;
import com.test.dto.response.RoleResponse;
import com.test.entity.Permission;
import com.test.entity.Role;
import com.test.repository.PermissionRepository;
import com.test.repository.RoleRepository;
import com.test.service.RoleService;
import com.test.utils.MapperUtils;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public RoleResponse create(RoleRequest request) {
        this.checkRoleIfExist(request.getName());
        Set<Permission> setPermissions = request.getPermissionId() != null ? getPermissionsFromIds(request.getPermissionId()) : null;
        Role role = new Role(request.getName(), setPermissions);
        repository.save(role);
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getPermissions()
        );
    }

    @Override
    @Transactional
    public RoleResponse update(Long id, RoleRequest request) {
        Role role = this.checkRoleExist(id);
        role.setName(request.getName());
        Set<Permission> setPermissions = getPermissionsFromIds(request.getPermissionId());
        role.setPermissions(setPermissions);
        repository.save(role);
        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getPermissions()
        );
    }

    @Override
    public List<RoleResponse> viewAll() {
        List<Role> list = repository.findAll();
        return MapperUtils.toDTOs(list, RoleResponse.class);
    }

    @Override
    public void delete(Long id) {
        Role role = this.checkRoleExist(id);
        repository.delete(role);
    }

    private Role checkRoleExist(Long id) {
        Optional<Role> role = repository.findById(id);
        if (role.isEmpty()) {
            throw new NotFoundException("Role " + id + " does not exist");
        }
        return role.get();

    }

    private void checkRoleIfExist(String name) {
        Optional<Role> optionalRole = repository.findRoleByName(name);
        if (optionalRole.isPresent()) {
            throw new IllegalArgumentException("Role " + name + " already exists");
        }
    }

    private Set<Permission> getPermissionsFromIds(Set<Long> permissionIds) {
        if(permissionIds.isEmpty()){
            throw new NotFoundException("Permission does not exist");
        }
        Set<Permission> setPermissions = new HashSet<>();
        for (Long perId : permissionIds) {
            Optional<Permission> permission = permissionRepository.findById(perId);
            if (permission.isEmpty()) {
                throw new NotFoundException("Permission " + perId + " does not exist");
            }
            setPermissions.add(permission.get());
        }
        return setPermissions;
    }

}
