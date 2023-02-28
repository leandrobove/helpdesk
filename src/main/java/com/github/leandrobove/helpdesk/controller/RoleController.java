package com.github.leandrobove.helpdesk.controller;

import com.github.leandrobove.helpdesk.model.Role;
import com.github.leandrobove.helpdesk.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    private static Logger log = LoggerFactory.getLogger(RoleController.class);

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("roles", roleService.findAll());

        return "roles/index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("role", new Role());

        return "roles/create";
    }

    @PostMapping
    public String save(@ModelAttribute("role") Role role, Model model) {
        roleService.create(role);

        return "redirect:/roles";
    }

    @DeleteMapping("/{roleId}")
    public String delete(@PathVariable Long roleId, Model model) {
        try {
            roleService.delete(roleId);
        } catch (IllegalArgumentException exception) {
            log.error("Error deleting role: {}", exception.getMessage());
        }

        return "redirect:/roles";
    }
}
