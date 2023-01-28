package com.ironhack.iron_bank_project.dtos.dtoTransaction;

import com.ironhack.iron_bank_project.utils.Money;
import lombok.Data;

@Data
public class TransactionThirdPartyRequest {

    private String username;

    private Money amount;

    private Long accountId;

    private String accountSecretKey;

    //TODO : Required header for the HushedKey in the Controller, pass de HashedKey as a BearerToken:
}
