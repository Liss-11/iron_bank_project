package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class CreditCardAccount extends Account{

    /*Balance
PrimaryOwner
Optional SecondaryOwner
CreditLimit
InterestRate
PenaltyFee*/
    @Timestamp
    private LocalDateTime interestPaymentDate = LocalDateTime.now().plusMonths(1L);
    private final BigDecimal PENALITY_FEE = BigDecimal.valueOf(40.0);

    //default = 100€
    //can be between 100€ and 100.000€
    private BigDecimal creditLimit = BigDecimal.valueOf(100.0);

   // default = 0.2%
   // can be between 0.2% and 0.1% -> APPLIED Monthly
    private BigDecimal interestRate = BigDecimal.valueOf(0.2);


    public CreditCardAccount() {

    }
}
