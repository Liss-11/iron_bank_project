package com.ironhack.iron_bank_project.transactions.dtos.response;

import com.ironhack.iron_bank_project.enums.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private LocalDateTime date;
    private Long account_id;
    private AccountType account_type;

    private BigDecimal amount;
}
