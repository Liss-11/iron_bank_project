package com.ironhack.iron_bank_project.accounts.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UpdateCheckingAccountRequest {

    @Positive
    @Digits(integer = 20, fraction = 2)
    @NotNull(message = "Balance must not be empty")
    private BigDecimal initialAmount;

    @NotBlank(message = "Primary Owner_ID must not be blank")
    private String PrimaryOwnerId;

    private String secondaryOwnerId;

    @NotBlank(message = "SecretKey must not be blank")
    private String secretKey;

    private String accountStatus;
}
