package com.ironhack.iron_bank_project.accounts.dtos.request;

import com.ironhack.iron_bank_project.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;

public class ChangeAccountStatusRequest {

    @NotBlank(message = "Status must not be blank")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AccountStatus getAccountStatus() {
        return AccountStatus.valueOf(status);
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.status = accountStatus.toString();
    }
}
