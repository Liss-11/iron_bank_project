package com.ironhack.iron_bank_project.dtos.dtoAuthentication.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]+", message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, max = 12,  message = "Password size must be between 5 and 12 characters")

    private String password;
}
