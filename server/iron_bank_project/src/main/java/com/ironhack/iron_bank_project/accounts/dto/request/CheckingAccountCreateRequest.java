package com.ironhack.iron_bank_project.accounts.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
public class CheckingAccountCreateRequest {

    @Positive
    @Digits(integer = 20, fraction = 2)
    @NotNull(message = "Balance must not be empty")
    private BigDecimal initialAmount;

    @NotBlank(message = "Primary Owner_ID must not be blank")
    private String idPrimaryOwner;

    private String secondaryOwnerId;

    @NotBlank(message = "SecretKey must not be blank")
    private String secretKey;

}
