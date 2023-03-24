package com.github.leandrobove.helpdesk.domain.service;

import com.github.leandrobove.helpdesk.domain.model.Role;
import com.github.leandrobove.helpdesk.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role create(Role role) {
        role.setName(role.getName().toUpperCase());

        return roleRepository.save(role);
    }

    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException(String.format("Role id %d not found", roleId)));
    }

    public void delete(Long roleId) {
        Role role = this.findById(roleId);

        roleRepository.delete(role);
    }
}
