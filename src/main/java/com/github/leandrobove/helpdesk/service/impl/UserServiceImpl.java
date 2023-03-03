package com.github.leandrobove.helpdesk.service.impl;

import com.github.leandrobove.helpdesk.dto.UserRequest;
import com.github.leandrobove.helpdesk.model.User;
import com.github.leandrobove.helpdesk.repository.UserRepository;
import com.github.leandrobove.helpdesk.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(String.format("User id %d not found", userId)));
    }

    @Override
    public void delete(Long userId) {
        User user = this.findById(userId);

        userRepository.delete(user);
    }

    @Override
    public void update(Long userId, User newUser) {
        User user = this.findById(userId);

        user.setName(newUser.getName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        if(newUser.isActive()) {
            user.activate();
        } else {
            user.deactivate();
        }

        userRepository.save(user);
    }
}
