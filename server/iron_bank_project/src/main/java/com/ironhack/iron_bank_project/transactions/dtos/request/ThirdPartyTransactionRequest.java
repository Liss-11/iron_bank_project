package com.ironhack.iron_bank_project.transactions.dtos.request;

import com.ironhack.iron_bank_project.utils.Money;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ThirdPartyTransactionRequest {
    private String username;

    private BigDecimal amount;

    private Long accountId;

    private String accountSecretKey;

    public ThirdPartyTransactionRequest() {
    }

    public ThirdPartyTransactionRequest(String username, BigDecimal amount, Long accountId, String accountSecretKey) {
        this.username = username;
        this.amount = amount;
        this.accountId = accountId;
        this.accountSecretKey = accountSecretKey;
    }

    //TODO : Required header for the HushedKey in the Controller, pass de HashedKey as a BearerToken:
}
