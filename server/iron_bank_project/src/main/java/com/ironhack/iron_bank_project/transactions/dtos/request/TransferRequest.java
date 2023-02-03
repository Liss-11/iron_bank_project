package com.ironhack.iron_bank_project.transactions.dtos.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {

    @NotBlank(message = "You must indicate who makes the transfer")
    private String fromAccountId;

    @NotBlank(message = "You must indicate who receives the transfer")
    private String toAccountId;

    @Positive
    @Digits(integer = 6, fraction = 2)
    @NotNull(message = "You must indicate the AMOUNT to transfer")
    private BigDecimal Amount;

}
