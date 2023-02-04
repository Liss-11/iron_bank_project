package com.ironhack.iron_bank_project.accounts.repository;

import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByStatus(AccountStatus status);

    List<Account> findByAccountType(AccountType type);


    List<Account> findByPrimaryOwnerId(String id);
}
