package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.accounts.dtos.request.*;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    @GetMapping("/admin/all_account/long_version")
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

    @GetMapping("/admin/all_accounts")
    public ResponseEntity<?> getAccountsShortVersion(
            @RequestParam Optional <String> status,
            @RequestParam Optional <String> type
    ){
        return accountService.getAccounts(type, status);
    }

    @GetMapping("/customer/my_accounts")
    public ResponseEntity<?> getMyAccounts(){
        return accountService.getMyAccounts();
    }

    @GetMapping("/admin/account/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PostMapping(value = "/admin/create/checking_account", consumes = "application/json")
    public ResponseEntity<?> createCheckingAccount(
            @Valid @RequestBody CreateCheckingAccountRequest request
    ){
        return accountService.createCheckingAccount(request);
    }

    @PostMapping(value = "/admin/create/saving_account", consumes = "application/json")
    public ResponseEntity<?> createSavingAccount(
            @Valid @RequestBody CreateSavingAccountRequest request
    ){
        return accountService.createSavingAccount(request);
    }

    @PostMapping(value = "/admin/create/credit_card", consumes = "application/json")
    public ResponseEntity<?> createCreditCard(
            @Valid @RequestBody CreateCreditCardAccountRequest request
    ){
        return accountService.createCreditCard(request);
    }

    @PutMapping("/admin/update/checking_account/{id}")
    public ResponseEntity<?> updateCheckingAccount(@PathVariable (name = "id") Long id,
                                          @Valid @RequestBody UpdateCheckingAccountRequest request){
        return accountService.updateCheckingAccount(id, request);
    }

    //TODO
    @PutMapping("/admin/update/saving_account/{id}")
    public ResponseEntity<?> updateSavingAccount(@PathVariable (name = "id") Long id,
                                                   @Valid @RequestBody UpdateSavingAccountRequest request){
        return accountService.updateSavingAccount(id, request);
    }

    //TODO
    @PutMapping("/admin/update/credit_card/{id}")
    public ResponseEntity<?> updateCreditCardAccount(@PathVariable (name = "id") Long id,
                                                   @Valid @RequestBody UpdateCreditCardAccountRequest request){
        return accountService.updateCreditCardAccount(id, request);
    }



    @PatchMapping("/admin/change_status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable (name = "id") Long id,
                                          @Valid @RequestBody ChangeAccountStatusRequest request){
        return accountService.changeStatus(id, request);
    }


    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteAccountById(
            @PathVariable (name = "id") Long id
    ){
        return accountService.deleteAccountById(id);
    }

}
