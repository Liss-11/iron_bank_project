package com.ironhack.iron_bank_project.transactions.dtos.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotNull(message = "You must indicate the accountId of the account you make transfer FROM")
    private Long fromAccountId;

    @NotNull(message = "You must indicate the accountId of the account you make transfer TO")
    private Long toAccountId;

    @Positive
    @Digits(integer = 6, fraction = 2)
    @NotNull(message = "You must indicate the AMOUNT to transfer")
    private BigDecimal amount;

    @NotBlank (message = "You must add a breve description, between 5 and 500 words")
    @Size(min = 5, max = 500)
    private String description;

}
