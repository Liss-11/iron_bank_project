package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterAdminRequest;
import com.ironhack.iron_bank_project.users.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/")
public class AdminController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/register/admin", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerAdmin(
            @Valid @RequestBody RegisterAdminRequest request
    ){
        return authenticationService.registerAdmin(request);
    }
}
