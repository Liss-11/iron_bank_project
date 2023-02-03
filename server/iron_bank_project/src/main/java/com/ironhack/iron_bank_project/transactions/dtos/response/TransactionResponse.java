package com.ironhack.iron_bank_project.transactions.dtos.response;

import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.enums.TransactionType;
import com.ironhack.iron_bank_project.transactions.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {

    private LocalDateTime date;
    private Long accountFromId;
    private BigDecimal initialAmount;
    private TransactionType transactionType;
    private Long accountToId;
    private BigDecimal sentAmount;
    private BigDecimal actualAmount;

    public TransactionResponse() {
    }

    public TransactionResponse(LocalDateTime date, Long accountFromId, BigDecimal initialAmount, TransactionType transactionType, Long accountToId, BigDecimal sentAmount, BigDecimal actualAmount) {
        this.date = date;
        this.accountFromId = accountFromId;
        this.initialAmount = initialAmount;
        this.transactionType = transactionType;
        this.accountToId = accountToId;
        this.sentAmount = sentAmount;
        this.actualAmount = actualAmount;
    }
}
