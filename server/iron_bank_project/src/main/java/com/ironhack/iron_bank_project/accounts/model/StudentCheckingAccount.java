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

    private String secretKey;

    public StudentCheckingAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, String secretKey) {
        super(balance, AccountType.STUDENT, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }

    public StudentCheckingAccount() {
    }
}
