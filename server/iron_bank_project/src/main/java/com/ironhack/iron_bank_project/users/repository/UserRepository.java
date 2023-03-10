package com.ironhack.iron_bank_project.users.repository;

import com.ironhack.iron_bank_project.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional <User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
