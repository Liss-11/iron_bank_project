package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CheckingAccount extends Account {

    @Timestamp
    private LocalDateTime maintenanceFeeDate = LocalDateTime.now().plusMonths(1L);

    private String secretKey;

    private  BigDecimal minimumBalance = BigDecimal.valueOf(250.0);

    private final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.valueOf(12.0);

    private final BigDecimal PENALITY_FEE = BigDecimal.valueOf(40.0);


    public CheckingAccount(BigDecimal balance, AccountType accountType, AccountStatus status, User primaryOwner, User secondaryOwner, String secretKey, BigDecimal minimumBalance) {
        super(balance, accountType, status, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
    }

    public CheckingAccount() {

    }
}
