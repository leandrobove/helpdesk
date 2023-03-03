package com.github.leandrobove.helpdesk.service.impl;

import com.github.leandrobove.helpdesk.exception.BusinessException;
import com.github.leandrobove.helpdesk.model.User;
import com.github.leandrobove.helpdesk.repository.UserRepository;
import com.github.leandrobove.helpdesk.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        //check if email account already exists
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        if (userExists.isPresent() && !userExists.get().equals(user)) {
            throw new BusinessException(String.format("This email address %s is already associated with an account", user.getEmail()));
        }

        //set an encrypted password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        if (newUser.isActive()) {
            user.activate();
        } else {
            user.deactivate();
        }

        userRepository.save(user);
    }
}
