package com.test.entity;

import com.test.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permission")
public class Permission extends BaseEntity {

    @Column(name = "name")
    private String name;

    /**
     * The URI associated with the permission.
     */
    @Column(name = "uri")
    private String uri;

    @Column(name = "method")
    private String method;
}
