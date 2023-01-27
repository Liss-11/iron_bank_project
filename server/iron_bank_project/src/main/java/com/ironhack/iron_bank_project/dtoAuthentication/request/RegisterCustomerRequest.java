package com.ironhack.iron_bank_project.dtoAuthentication.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterCustomerRequest {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 30)
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]+", message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, max = 12,  message = "Password size must be between 5 and 12 characters")
    private String password;
    @NotBlank(message = "Date of Birth must not be blank")
    //FORMAT : 0000-00-00 (yyyy-mm-dd)
    private String dateOfBirth;
    @NotBlank(message = "Street must not be blank")
    private String street;
    @NotBlank(message = "City must not be blank")

    private String city;

    @NotBlank(message = "postalCode must not be blank")

    private String postalCode;

    @NotBlank(message = "Country must not be blank")
    private String country;
}
