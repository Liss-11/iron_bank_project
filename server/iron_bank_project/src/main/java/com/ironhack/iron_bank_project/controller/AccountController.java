package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.accounts.dtos.request.*;
import com.ironhack.iron_bank_project.accounts.dtos.response.GetAccountResponse;
import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    @GetMapping("/long_version")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccountsLongVersion(
            @RequestParam Optional <String> status,
            @RequestParam Optional <String> type
    ){
        if(status.isPresent()){
            return ResponseEntity.ok(accountRepository.findByStatus(AccountStatus.valueOf(status.get())));
        }else if(type.isPresent()){
            return ResponseEntity.ok(accountRepository.findByAccountType(AccountType.valueOf(type.get())));
        }
        return ResponseEntity.ok(accountRepository.findAll());
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccountsShortVersion(
            @RequestParam Optional <String> status,
            @RequestParam Optional <String> type
    ){
        return accountService.getAccounts(type, status);
    }

    @GetMapping("/my_accounts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMyAccounts(){
        return accountService.getMyAccounts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

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

    @PutMapping("/update/checking_account/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCheckingAccount(@PathVariable (name = "id") Long id,
                                          @Valid @RequestBody UpdateCheckingAccountRequest request){
        return accountService.updateCheckingAccount(id, request);
    }

    //TODO
    @PutMapping("/update/saving_account/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSavingAccount(@PathVariable (name = "id") Long id,
                                                   @Valid @RequestBody UpdateSavingAccountRequest request){
        return accountService.updateSavingAccount(id, request);
    }

    //TODO
    @PutMapping("/update/credit_card/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCreditCardAccount(@PathVariable (name = "id") Long id,
                                                   @Valid @RequestBody UpdateCreditCardAccountRequest request){
        return accountService.updateCreditCardAccount(id, request);
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

}
