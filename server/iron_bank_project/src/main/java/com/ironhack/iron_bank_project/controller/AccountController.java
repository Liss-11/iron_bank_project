package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.accounts.dto.request.ChangeAccountStatusRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.CreateCheckingAccountRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.CreateCreditCardAccountRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.CreateSavingAccountRequest;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //getAllAccounts

    //updateAccount

    //getAccountById

    //deleteAccount

    @PostMapping(value = "/create/checking_account", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCheckingAccount(
            @Valid @RequestBody CreateCheckingAccountRequest request
    ){
        return accountService.createCheckingAccount(request);
    }

    @PostMapping(value = "/create/saving_account", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSavingAccount(
            @Valid @RequestBody CreateSavingAccountRequest request
    ){
        return accountService.createSavingAccount(request);
    }

    @PostMapping(value = "/create/credit_card", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCreditCard(
            @Valid @RequestBody CreateCreditCardAccountRequest request
    ){
        return accountService.createCreditCard(request);
    }

    @PatchMapping("/change_status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable (name = "id") Long id,
                                          @Valid @RequestBody ChangeAccountStatusRequest request){
        return accountService.changeStatus(id, request);
    }



    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccountById(
            @PathVariable (name = "id") Long id
    ){
        return accountService.deleteAccountById(id);
    }







/*
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable(name = "id") String id,
                                        @Valid @RequestBody UpdateCustomerRequest request){
        return userService.updateUser(id, request);
    }

    @PatchMapping("/change_status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable (name = "id") String id,
                                          @Valid @RequestBody ChangeStatusRequest request){
        return userService.changeStatus(id, request);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserById(
            @PathVariable (name = "id") String id
    ){
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/delete/current_customer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteActualCustomer(){
        return userService.deleteActualUser();
    }
*/

}
