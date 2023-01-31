package com.ironhack.iron_bank_project.accounts.repository;

import com.ironhack.iron_bank_project.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
