package com.test.entity;

import com.test.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {
    @Column(name = "name")
    private String name;

    /**
     * Set of permissions associated with the role. FetchType.EAGER ensures that permissions are eagerly fetched.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}
