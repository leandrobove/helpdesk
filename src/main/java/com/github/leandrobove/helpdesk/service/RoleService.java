package com.github.leandrobove.helpdesk.service;

import com.github.leandrobove.helpdesk.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role create(Role role);

    Role findById(Long roleId);

    void delete(Long roleId);
}
