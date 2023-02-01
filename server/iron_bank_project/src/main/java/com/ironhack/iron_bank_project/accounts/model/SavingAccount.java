package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dto.request.CreateSavingAccountRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class SavingAccount extends Account implements FeesInterface {

    private String secretKey;
    private BigDecimal minimumBalance;
    private final BigDecimal penalityFee = BigDecimal.valueOf(40.0);
    @Timestamp
    private LocalDateTime interestPaymentDate = LocalDateTime.now().plusYears(1L);
    @Column(precision = 10, scale = 4)
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    public SavingAccount() {}

    public SavingAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, AccountType.SAVING, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);

    }

    public static SavingAccount savingAccountFromDTO(CreateSavingAccountRequest request, User owner, User secondaryOwner) {
        return new SavingAccount(
                request.getInitialAmount(),
                owner,
                secondaryOwner,
                request.getSecretKey(),
                request.getMinimumBalance(),
                request.getInterestRate()
        );
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        if(minimumBalance == null){
            setMinimumBalance(BigDecimal.valueOf(1000.0));
        }
        else if(minimumBalance.compareTo(BigDecimal.valueOf(100)) < 0 || minimumBalance.compareTo(BigDecimal.valueOf(1000)) > 0){
            throw new IllegalArgumentException("MinimumBalance must be between 100€ and 1000€");
        }
        else if (minimumBalance.compareTo(getBalance()) > 0){
            throw new IllegalArgumentException("Balance must be greater than the minimumBalance, put more money in the Account");
        }
        else{this.minimumBalance = minimumBalance;}
    }

    public void setInterestRate(BigDecimal interestRate){
        if(interestRate == null){
            setMinimumBalance(BigDecimal.valueOf(0.0025));
        }
        else if(interestRate.compareTo(BigDecimal.valueOf(0.0025)) < 0 || interestRate.compareTo(BigDecimal.valueOf(0.5)) > 0){
            throw new IllegalArgumentException("InterestRate must be between 0.0025% and 0.5%");
        }
        else{this.interestRate = interestRate;}

    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public BigDecimal penalityFee() {
        return penalityFee;
    }

    public LocalDateTime getInterestPaymentDate() {
        return interestPaymentDate;
    }

    public void setInterestPaymentDate(LocalDateTime interestPaymentDate) {
        this.interestPaymentDate = interestPaymentDate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

}
