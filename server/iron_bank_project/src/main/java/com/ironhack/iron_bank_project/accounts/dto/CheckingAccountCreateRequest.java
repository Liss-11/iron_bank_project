package com.ironhack.iron_bank_project.accounts.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
public class CheckingAccountCreateRequest {

    private BigDecimal initialAmount;

    private String primaryOwnerId;

    private Optional<String> secondaryOwnerId;

    private String secretKey;
}
