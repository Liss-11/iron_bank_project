package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.dtoRequest.AuthenticationRequest;
import com.ironhack.iron_bank_project.dtoRequest.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.dtoResponse.AuthenticationResponse;
import com.ironhack.iron_bank_project.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iron_bank/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register/customer", consumes = "application/json")
    public ResponseEntity<?> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request
    ){
        return authenticationService.registerCustomer(request);
    }

   /* @PostMapping(value = "/register/customer", consumes = "application/json")
    public ResponseEntity<AuthenticationResponse> registerThirdParty(
            @Valid @RequestBody RegisterThirdPartyRequest request
    ){
        return ResponseEntity.ok(authenticationService.registerThirdParty(request));

    }*/

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }
}
