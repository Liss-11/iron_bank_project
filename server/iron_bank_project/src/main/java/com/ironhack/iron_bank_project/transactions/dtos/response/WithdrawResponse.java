package com.ironhack.iron_bank_project.transactions.dtos.response;

import com.ironhack.iron_bank_project.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WithdrawResponse {

    private LocalDateTime date;
    private Long accountId;
    private BigDecimal initialAmount;
    private TransactionType transactionType;
    private BigDecimal withdrawnAmount;

    private BigDecimal actualAmount;

    public WithdrawResponse() {
    }

    public WithdrawResponse(LocalDateTime date, Long accountId, BigDecimal initialAmount, TransactionType transactionType, BigDecimal withdrawnAmount, BigDecimal actualAmount) {
        this.date = date;
        this.accountId = accountId;
        this.initialAmount = initialAmount;
        this.transactionType = transactionType;
        this.withdrawnAmount = withdrawnAmount;
        this.actualAmount = actualAmount;
    }
}
