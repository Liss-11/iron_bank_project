package com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request;

import com.ironhack.iron_bank_project.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeStatusRequest {

    @NotBlank (message = "Status must not be blank")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserStatus getUserStatus() {
        return UserStatus.valueOf(status);
    }

    public void setUserStatus(UserStatus employeeStatus) {
        this.status = employeeStatus.toString();
    }
}
