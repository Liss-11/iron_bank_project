package com.ironhack.iron_bank_project.accounts.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountResponse {

    private LocalDateTime creationDate;
    private String ownerId;
    private Long accountId;
    private String AccountType;
    private String AccountStatus;
    private BigDecimal AccountBalance;

}
