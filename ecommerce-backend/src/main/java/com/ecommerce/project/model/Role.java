// Role entity - represents user roles like USER, ADMIN
package com.ecommerce.project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    // Unique ID for each role
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    // Role name (stored as string in database)
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private AppRole roleName;

    // Default constructor
    public Role() {
    }

    // Constructor with all fields
    public Role(Integer roleId, AppRole roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    // Constructor with role name only
    public Role(AppRole roleName) {
        this.roleName = roleName;
    }

    // Get role ID
    public Integer getRoleId() {
        return roleId;
    }

    // Set role ID
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    // Get role name
    public AppRole getRoleName() {
        return roleName;
    }

    // Set role name
    public void setRoleName(AppRole roleName) {
        this.roleName = roleName;
    }
}
