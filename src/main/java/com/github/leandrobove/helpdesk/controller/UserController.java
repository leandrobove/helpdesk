package com.github.leandrobove.helpdesk.controller;

import com.github.leandrobove.helpdesk.dto.UserRequest;
import com.github.leandrobove.helpdesk.model.User;
import com.github.leandrobove.helpdesk.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserRequest userRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        userService.create(userRequest.toModel());

        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String edit(@PathVariable Long userId, Model model) {
        return "users/edit";
    }
}
