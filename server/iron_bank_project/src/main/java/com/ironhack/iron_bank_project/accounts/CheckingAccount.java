package com.ironhack.iron_bank_project.accounts;

import com.ironhack.iron_bank_project.enums.StatusEnum;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class CheckingAccount extends Account{

    private String secretKey;

    private final BigDecimal MINIMUM_BALANCE = BigDecimal.valueOf(250.0);

    private final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.valueOf(12.0);

    @CreationTimestamp
    private Instant creationDate;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public CheckingAccount(BigDecimal balance, User primaryOwner, String secretKey, StatusEnum status) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
        setCreationDate(getCreationDate());
        setStatus(status);
    }

    public CheckingAccount() {

    }
}
