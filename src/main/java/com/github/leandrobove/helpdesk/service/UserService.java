package com.github.leandrobove.helpdesk.service;

import com.github.leandrobove.helpdesk.model.Role;
import com.github.leandrobove.helpdesk.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User create(User user);

    User findById(Long userId);

    void delete(Long userId);

    void update(Long userId, User user);
}
