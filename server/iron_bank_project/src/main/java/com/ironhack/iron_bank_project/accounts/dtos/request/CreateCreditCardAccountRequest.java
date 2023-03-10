package com.ironhack.iron_bank_project.accounts.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCreditCardAccountRequest {
    @NotNull
    @Positive
    private long associatedToAccountId;

    @NotBlank(message = "Primary Owner_ID must not be blank")
    private String PrimaryOwnerId;

    private String secondaryOwnerId;

    @DecimalMax(value = "1000", message = "Credit limit must be between 100€ and 1000€")
    @DecimalMin(value = "100", message = "Credit limit must be between 100€ and 1000€")
    @Digits(integer = 4, fraction = 2)
    private BigDecimal creditLimit;

    @DecimalMax(value = "0.2", message = "Interest must be between 0.1% and 0.2%")
    @DecimalMin(value = "0.1", message = "Interest must be between 0.1% and 0.2%")
    @Digits(integer = 0, fraction = 2)
    private BigDecimal interestRate;



}
