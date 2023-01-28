package com.ironhack.iron_bank_project.users.repository;

import com.ironhack.iron_bank_project.users.model.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
