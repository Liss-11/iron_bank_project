package com.ironhack.iron_bank_project.accounts.dtos.request;

import com.ironhack.iron_bank_project.enums.AccountStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

//TODO -> copied params, you must adapt them to the class
@Data
public class UpdateCreditCardAccountRequest {
    @Positive
    @Digits(integer = 20, fraction = 2)
    private BigDecimal balance;

    private String secondaryOwnerId;

    @Size(min = 10, max = 10, message = "The secret key must contain 10 characters")
    private String secretKey;

    private String status;

    private BigDecimal minimumBalance;

    public String getStatus(){
        return status;
    }

    public AccountStatus getAccountStatus() {
        return AccountStatus.valueOf(status);
    }
}
