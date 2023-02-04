package com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCustomerRequest {
    @Size(max = 30)
    private String name;

    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]+", message = "Email format is invalid.")
    private String email;

    @Size(min = 5, max = 12,  message = "Password size must be between 5 and 12 characters")
    private String password;
    @Pattern(regexp = "^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", message = "Date format must be YYYY-MM-DD")
    private String dateOfBirth;
    private String street;

    private String city;
    private String postalCode;
    private String country;
}
