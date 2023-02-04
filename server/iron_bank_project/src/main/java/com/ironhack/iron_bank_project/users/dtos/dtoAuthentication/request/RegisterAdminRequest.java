package com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterAdminRequest {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 30)
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]+", message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, max = 12,  message = "Password size must be between 5 and 12 characters")
    private String password;

    public RegisterAdminRequest() {
    }

    public RegisterAdminRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
