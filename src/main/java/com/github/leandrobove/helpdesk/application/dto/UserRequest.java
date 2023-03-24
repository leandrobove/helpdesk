package com.github.leandrobove.helpdesk.application.dto;

import com.github.leandrobove.helpdesk.domain.model.Role;
import com.github.leandrobove.helpdesk.domain.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

public class UserRequest implements Serializable {

    @Email(message = "Please provide a valid email")
    @NotBlank(message = "email field cannot be empty")
    private String email;

    @NotBlank(message = "name field cannot be empty")
    private String name;

    @NotBlank(message = "last name field cannot be empty")
    private String lastName;

    @NotBlank(message = "password field cannot be empty")
    private String password;

    private boolean active;

    @NotNull(message = "roles field cannot be null")
    private Set<Role> roles;

    public UserRequest() {
    }

    public UserRequest(String email, String name, String lastName, String password, boolean active, Set<Role> roles) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.active = active;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User toModel() {
        User user = new User(null, this.getEmail(), this.getName(), this.getLastName(), this.getPassword());
        if (this.isActive()) {
            user.activate();
        } else {
            user.deactivate();
        }

        this.getRoles().stream().forEach(role -> user.addRole(role));

        return user;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", roles=" + roles +
                '}';
    }
}
