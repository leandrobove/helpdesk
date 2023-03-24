package com.github.leandrobove.helpdesk.domain.repository;

import com.github.leandrobove.helpdesk.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.roles r where u.email = :email")
    Optional<User> findByEmail(String email);
}
