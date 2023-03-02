package com.github.leandrobove.helpdesk.controller;

import com.github.leandrobove.helpdesk.model.User;
import com.github.leandrobove.helpdesk.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

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

    @GetMapping("/{userId}")
    public String edit(@PathVariable Long userId, Model model) {
        return "users/edit";
    }
}
