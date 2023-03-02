package com.github.leandrobove.helpdesk.dto;

import com.github.leandrobove.helpdesk.model.Role;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoleRequest implements Serializable {

    private Long id;

    @NotBlank(message = "Role name cannot be blank")
    private String name;

    public RoleRequest() {
    }

    public RoleRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role toModel() {
        return new Role(this.getId(), this.getName());
    }
}
