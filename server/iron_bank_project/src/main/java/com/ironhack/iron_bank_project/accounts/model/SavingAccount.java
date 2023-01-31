package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class SavingAccount extends Account {

    //default = 1000€
    //can be between 100€ and 1000
    private String secretKey;
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000.0);

    @Timestamp
    private LocalDateTime interestPaymentDate = LocalDateTime.now().plusYears(1L);


    //default = 0.0025%
    //can be between = 0.0025% and 0.5%
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    public SavingAccount(BigDecimal balance, AccountType accountType, AccountStatus status, User primaryOwner, User secondaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, accountType, status, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public SavingAccount() {

    }
}
