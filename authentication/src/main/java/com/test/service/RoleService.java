package com.test.service;

import com.test.dto.request.RoleRequest;
import com.test.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    RoleResponse update(Long id, RoleRequest request);

    List<RoleResponse> viewAll();

    void delete(Long id);
}
