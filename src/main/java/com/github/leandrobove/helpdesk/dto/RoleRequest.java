package com.github.leandrobove.helpdesk.dto;

import com.github.leandrobove.helpdesk.model.Role;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoleRequest implements Serializable {

    @NotBlank(message = "Role name cannot be blank")
    private String name;

    public RoleRequest() {
    }

    public RoleRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role toModel() {
        return new Role(null, this.getName());
    }
}
