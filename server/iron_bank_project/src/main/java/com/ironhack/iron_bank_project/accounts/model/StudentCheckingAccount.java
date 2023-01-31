package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class StudentCheckingAccount extends Account {

    public StudentCheckingAccount(BigDecimal balance, AccountType accountType, AccountStatus status, User primaryOwner, User secondaryOwner) {
        super(balance, accountType, status, primaryOwner, secondaryOwner);
    }

    public StudentCheckingAccount() {
    }
}
