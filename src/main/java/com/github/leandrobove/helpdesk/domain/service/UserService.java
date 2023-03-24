package com.github.leandrobove.helpdesk.domain.service;

import com.github.leandrobove.helpdesk.domain.exception.BusinessException;
import com.github.leandrobove.helpdesk.domain.model.Role;
import com.github.leandrobove.helpdesk.domain.model.User;
import com.github.leandrobove.helpdesk.domain.repository.RoleRepository;
import com.github.leandrobove.helpdesk.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        //check if email account already exists
        Optional<User> findByEmail = userRepository.findByEmail(user.getEmail());

        if (findByEmail.isPresent())
            throw new BusinessException(String.format("This email address %s is already associated with an account", user.getEmail()));


        //set an encrypted password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set USER authority
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);

        return userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(String.format("User id %d not found", userId)));
    }

    public void delete(Long userId) {
        User user = this.findById(userId);

        userRepository.delete(user);
    }

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
