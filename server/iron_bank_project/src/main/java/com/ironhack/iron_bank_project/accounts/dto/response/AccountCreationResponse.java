package com.ironhack.iron_bank_project.accounts.dto.response;

import com.ironhack.iron_bank_project.accounts.model.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountCreationResponse {

    private Long accountId;

    private String userName;

    private String userId;

    private BigDecimal balance;

    public AccountCreationResponse() {
    }

    public AccountCreationResponse(Long accountId, String userName, String userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userName = userName;
        this.userId = userId;
        this.balance = balance;
    }

    public static AccountCreationResponse fromAccount(Account account){
        return new AccountCreationResponse(
                account.getId(),
                account.getPrimaryOwner().getName(),
                account.getPrimaryOwner().getId(),
                account.getBalance());
    }

    @Override
    public String toString() {
        String message = "Account successfully created!";
        return "message: " + message + '\n' +
                "accountId: " + accountId + '\n' +
                "userName: " + userName + '\n' +
                "userId: " + userId + '\n' +
                "balance: " + balance + "€";
    }

}
