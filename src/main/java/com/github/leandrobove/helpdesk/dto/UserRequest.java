package com.github.leandrobove.helpdesk.dto;

import com.github.leandrobove.helpdesk.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

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

    public UserRequest() {
    }

    public UserRequest(String email, String name, String lastName, String password) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
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

    public User toModel() {
        return new User(null, this.getEmail(), this.getName(), this.getLastName(), this.getPassword());
    }
}
