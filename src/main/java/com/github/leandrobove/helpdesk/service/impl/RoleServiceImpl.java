package com.github.leandrobove.helpdesk.service.impl;

import com.github.leandrobove.helpdesk.model.Role;
import com.github.leandrobove.helpdesk.repository.RoleRepository;
import com.github.leandrobove.helpdesk.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role create(Role role) {
        role.setName(role.getName().toUpperCase());

        return roleRepository.save(role);
    }

    @Override
    public Role findById(Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException(String.format("Role id %d not found", roleId)));
    }

    @Override
    public void delete(Long roleId) {
        Role role = this.findById(roleId);

        roleRepository.delete(role);
    }
}
