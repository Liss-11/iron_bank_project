package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.AuthenticationRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.ResetPasswordRequest;
import com.ironhack.iron_bank_project.users.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping(value = "/register/customer", consumes = "application/json")
    public ResponseEntity<?> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request
    ){
        return authenticationService.registerCustomer(request);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return authenticationService.authenticate(request);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return authenticationService.logout();
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        return authenticationService.forgotPassword(email);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
       return authenticationService.resetPassword(request);
    }

}
