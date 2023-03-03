package com.github.leandrobove.helpdesk.controller;

import com.github.leandrobove.helpdesk.dto.UserRequest;
import com.github.leandrobove.helpdesk.model.User;
import com.github.leandrobove.helpdesk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());

        return "users/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        userService.create(this.toModel(userRequest));

        return "redirect:/users";
    }

    @DeleteMapping("/{userId}")
    public String delete(@PathVariable Long userId, Model model) {
        try {
            userService.delete(userId);
        } catch (IllegalArgumentException exception) {
            log.error("Error deleting user: {}", exception.getMessage());
        }

        return "redirect:/users";
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable Long userId, Model model) {
        try {
            User user = userService.findById(userId);

            model.addAttribute("user", user);
        } catch (IllegalArgumentException e) {
            log.error("Error editing user: {}", e.getMessage());

            return "redirect:/users";
        }

        return "users/edit";
    }

    @PutMapping("/{userId}")
    public String update(@PathVariable Long userId, @Valid @ModelAttribute("user") UserRequest userRequest,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }

        User user = this.toModel(userRequest);
        if (userRequest.isActive()) {
            user.activate();
        } else {
            user.deactivate();
        }

        userService.update(userId, user);

        return "redirect:/users";
    }

    private User toModel(UserRequest userRequest) {
        return new User(null, userRequest.getEmail(), userRequest.getName(), userRequest.getLastName(), userRequest.getPassword());
    }

}
