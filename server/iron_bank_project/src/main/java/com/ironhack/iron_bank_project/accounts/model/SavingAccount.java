package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dto.request.CheckingAccountCreateRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.SavingAccountCreateRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;
import lombok.Data;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Data
@Entity
public class SavingAccount extends Account implements FeesInterface {

    private String secretKey;

    //default = 1000€
    //can be between 100€ and 1000
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000.0);

    private BigDecimal penalityFee = BigDecimal.valueOf(40.0);

    @Timestamp
    private LocalDateTime interestPaymentDate = LocalDateTime.now().plusYears(1L);


    //default = 0.0025%
    //can be between = 0.0025% and 0.5%
    @Column(precision = 10, scale = 4)
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025).setScale(4, RoundingMode.HALF_DOWN);

    public SavingAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, AccountType.SAVING, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public SavingAccount() {

    }

    public static SavingAccount savingAccountFromDTO(SavingAccountCreateRequest request, User owner, User secondaryOwner) {
        SavingAccount account = new SavingAccount();

        if(request.getInitialAmount().compareTo(account.getMinimumBalance()) < 0 ){
            throw new IllegalArgumentException("The initial amount must be greater than the MinimumBalance: 250€");
        }
        account.setBalance(request.getInitialAmount());
        account.setPrimaryOwner(owner);
        if(secondaryOwner != null){account.setSecondaryOwner(secondaryOwner);}
        account.setSecretKey(request.getSecretKey());





        account.setInterestRate();
        account.setMinimumBalance();
        return account;

        //checkers

        //guardar

        return account;

    }

  /*  public void setMinimumBalance(BigDecimal minimumBalance){
        if(minimumBalance != null){
            this.minimumBalance = BigDecimal.valueOf(1000.0);
        }
        if (minimumBalance <  || minimumBalance > 1000){

        }
    }*/

    public void setInterestRate(BigDecimal interestRate){

    }
}
