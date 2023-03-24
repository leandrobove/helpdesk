package com.github.leandrobove.helpdesk.application.controller;

import com.github.leandrobove.helpdesk.application.dto.UserRequest;
import com.github.leandrobove.helpdesk.domain.model.Role;
import com.github.leandrobove.helpdesk.domain.model.User;
import com.github.leandrobove.helpdesk.domain.service.RoleService;
import com.github.leandrobove.helpdesk.domain.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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
            List<Role> roles = roleService.findAll();

            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
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

        User user = userRequest.toModel();

        log.info("-------> User requested {}", userRequest);
        log.info("-------> User updated {}", user);

        userService.update(userId, user);

        return "redirect:/users";
    }

}
