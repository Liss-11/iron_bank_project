package com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "Email must not be blank")
    private String answer;
    @NotBlank(message = "Email must not be blank")
    private String newPassword;

}
