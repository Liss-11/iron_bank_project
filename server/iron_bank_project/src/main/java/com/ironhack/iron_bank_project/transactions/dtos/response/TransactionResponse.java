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
    private Long accountToId;
    private TransactionType transactionType;
    private BigDecimal sentAmount;
    private Long accountFromId;
    private BigDecimal actualAmount;

    public TransactionResponse() {
    }

    public TransactionResponse(LocalDateTime date, Long accountToId, TransactionType transactionType, BigDecimal sentAmount, Long accountFromId, BigDecimal actualAmount) {
        this.date = date;
        this.accountToId = accountToId;
        this.transactionType = transactionType;
        this.sentAmount = sentAmount;
        this.accountFromId = accountFromId;
        this.actualAmount = actualAmount;
    }
}
