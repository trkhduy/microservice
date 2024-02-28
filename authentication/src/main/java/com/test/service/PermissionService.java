package com.test.service;


import com.test.dto.request.PermissionRequest;
import com.test.dto.response.PermissionResponse;
import com.test.entity.Permission;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionRequest request);

    PermissionResponse update(Long id, PermissionRequest request);

    List<PermissionResponse> viewAll();

    List<Permission> getAll();

    void delete(Long id);
}
