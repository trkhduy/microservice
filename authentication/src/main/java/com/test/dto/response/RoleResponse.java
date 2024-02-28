package com.test.dto.response;

import com.test.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private Set<Permission> permissions;
}
