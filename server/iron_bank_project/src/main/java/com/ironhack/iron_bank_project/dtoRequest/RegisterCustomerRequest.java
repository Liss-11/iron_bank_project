package com.ironhack.iron_bank_project.dtoRequest;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterCustomerRequest {

    @NotBlank(message = "Name must not be blank")
    @Size(max = 30)
    private String name;

    //Try which one is better if @email or @pattern
    @NotBlank(message = "Email must not be blank")
   // @Email(message = "Incorrect email format")
   // @Pattern(regexp = "[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]+", message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, max = 12,  message = "Mail size must be between 5 and 12 characters")
   // @Pattern(regexp = "^((?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])){5,12}$",
          //  message = "password must contain at least 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
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

    public RegisterCustomerRequest() {
    }

    public RegisterCustomerRequest(String name, String email, String password, String dateOfBirth, String street, String city, String postalCode, String country) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
}
