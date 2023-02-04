package com.ironhack.iron_bank_project.transactions.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DepositResponse {
    private LocalDateTime date;

    private Long accountTO;

    private BigDecimal depositAmount;
    private String response;

    public DepositResponse() {
    }

    public DepositResponse(LocalDateTime date, Long accountTO, BigDecimal depositAmount) {
        this.date = date;
        this.accountTO = accountTO;
        this.depositAmount = depositAmount;
        setResponse("Deposit transaction is DONE!");
    }
}
