package com.ironhack.iron_bank_project.accounts.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCheckingAccountRequest {

    @Positive
    @Digits(integer = 20, fraction = 2)
    @NotNull(message = "Balance must not be empty")
    private BigDecimal initialAmount;

    @NotBlank(message = "Primary Owner_ID must not be blank")
    private String PrimaryOwnerId;

    private String secondaryOwnerId;

    @NotBlank(message = "SecretKey must not be blank")
    @Size(min = 10, max = 10, message = "The secret key must contain 10 characters")
    private String secretKey;

}
