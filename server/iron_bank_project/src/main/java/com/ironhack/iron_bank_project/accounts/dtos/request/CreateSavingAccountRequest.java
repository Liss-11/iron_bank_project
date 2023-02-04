package com.ironhack.iron_bank_project.accounts.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateSavingAccountRequest {

    @Positive
    @Digits(integer = 20, fraction = 2)
    @NotNull(message = "Balance must not be null")
    private BigDecimal initialAmount;

    @NotBlank(message = "Primary_Owner_ID must not be blank")
    private String primaryOwnerId;

    private String secondaryOwnerId;

    @DecimalMax(value = "0.5", message = "Interest must be between 0.0025% and 0.5%")
    @DecimalMin(value = "0.0025", message = "Interest must be between 0.0025% and 0.5%")
    @Digits(integer = 0, fraction = 4)
    private BigDecimal interestRate;

    @NotBlank(message = "SecretKey must no t be blank")
    @Size(min = 10, max = 10, message = "The secret key must contain 10 characters")
    private String secretKey;

    @Min(100L)
    @Max(1000L)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal minimumBalance;

}
