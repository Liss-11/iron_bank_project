package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dto.request.CheckingAccountCreateRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CheckingAccount extends Account implements FeesInterface {

    @Timestamp
    private LocalDateTime maintenanceFeeDate = LocalDateTime.now().plusMonths(1L);

    private String secretKey;

    private BigDecimal minimumBalance = BigDecimal.valueOf(250.0);

    private BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12.0);

    private BigDecimal penalityFee = BigDecimal.valueOf(40.0);
    public CheckingAccount() {
    }

    public CheckingAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, String secretKey) {
        super(balance, AccountType.CHECKING, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }

    public static CheckingAccount checkingAccountFromDTO (
            CheckingAccountCreateRequest request, User owner, User secondaryOwner){
        CheckingAccount account = new CheckingAccount();
        if(request.getInitialAmount().compareTo(account.getMinimumBalance()) < 0 ){
            throw new IllegalArgumentException("The initial amount must be greater than the MinimumBalance: 250€");
        }
        account.setBalance(request.getInitialAmount());
        account.setPrimaryOwner(owner);
        if(secondaryOwner != null){account.setSecondaryOwner(secondaryOwner);}
        account.setSecretKey(request.getSecretKey());
        return account;
    }

}
