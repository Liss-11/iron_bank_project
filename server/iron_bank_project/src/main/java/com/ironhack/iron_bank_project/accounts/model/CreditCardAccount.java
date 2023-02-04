package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dtos.request.CreateCreditCardAccountRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.utils.Money;
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

    private boolean penalized = false;

   @Column(precision = 10, scale = 4)
    private BigDecimal interestRate;

   @OneToOne()
   @JoinColumn(name = "principal_account")
   private Account associatedToAccount;

    @Override
    public void setBalance(Money balance){
        if(balance.getAmount().compareTo(creditLimit) > 0){
            if(!penalized) {
                balance = new Money(balance.increaseAmount(penalityFee));
                penalized = true;
            }
        }else{
            if(penalized){penalized = false;}
        }
        super.setBalance(balance);
    }


    public CreditCardAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate, Account associteToAccount) {
        super(balance, AccountType.CREDIT, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
        setAssociatedToAccount(associteToAccount);
    }

    public CreditCardAccount() {}

    public static CreditCardAccount fromDto(CreateCreditCardAccountRequest dto, User owner, User secondaryOwner, Account associteToAccount){
        return new CreditCardAccount(
                BigDecimal.valueOf(0.0),
                owner,
                secondaryOwner,
                dto.getCreditLimit(),
                dto.getInterestRate(),
                associteToAccount
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
        }else{
            this.creditLimit = creditLimit;
        }

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
