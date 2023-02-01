package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dto.request.CreateCreditCardAccountRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class CreditCardAccount extends Account implements FeesInterface {
    @Timestamp
    private LocalDateTime interestPaymentDate = LocalDateTime.now().plusMonths(1L);
    private BigDecimal penalityFee = BigDecimal.valueOf(40.0);

    private BigDecimal creditLimit;

   @Column(precision = 10, scale = 4)
    private BigDecimal interestRate;


    public CreditCardAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, AccountType.CREDIT, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCardAccount() {}

    public static CreditCardAccount fromDto(CreateCreditCardAccountRequest dto, User owner, User secondaryOwner){
        return new CreditCardAccount(
                dto.getInitialAmount(),
                owner,
                secondaryOwner,
                dto.getCreditLimit(),
                dto.getInterestRate()
        );
    }

    public void setCreditLimit(BigDecimal creditLimit){
        final BigDecimal MAX_CREDIT = new BigDecimal(1000);
        final BigDecimal MIN_CREDIT = new BigDecimal(100);
        if(creditLimit == null){
            setCreditLimit(BigDecimal.valueOf(100.0));
        }
        else if(creditLimit.compareTo(MAX_CREDIT) > 0 || creditLimit.compareTo(MIN_CREDIT) < 0){
            throw new IllegalArgumentException("Credit limit must be between 100 and 1000");
        }
        this.creditLimit = creditLimit;
    }

    public void setInterestRate(BigDecimal interestRate){
        final BigDecimal MAX_INTEREST = new BigDecimal("0.2");
        final BigDecimal MIN_INTEREST = new BigDecimal("0.1");
        if(interestRate == null){
            setInterestRate(BigDecimal.valueOf(0.2));
        }
        else if(interestRate.compareTo(MAX_INTEREST) > 0 || interestRate.compareTo(MIN_INTEREST) < 0){
            throw new IllegalArgumentException("Interest must be between 0.1% and 0.2%");
        }
        this.interestRate = interestRate;
    }
}
