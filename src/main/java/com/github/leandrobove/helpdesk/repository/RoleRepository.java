package com.github.leandrobove.helpdesk.repository;

import com.github.leandrobove.helpdesk.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
