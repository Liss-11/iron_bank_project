package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.accounts.dto.request.*;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private final AccountRepository accountRepository;

    @GetMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAccounts(
            @RequestParam Optional <AccountStatus> status,
            @RequestParam Optional <AccountType> type
    ){
        if(status.isPresent()){return ResponseEntity.ok(accountRepository.findByStatus(status));}
        return null;
    }

    /* public List<Employee> filterDoctors(@RequestParam Optional<Status> status, @RequestParam Optional<String> department) {
        if (status.isPresent()) {
            return employeeRepository.findByStatus(status.get());
        } else if (department.isPresent()) {
            return employeeRepository.findByDepartment(department.get());
        } else {
            return employeeRepository.findAll();
        }
    }
*/

    //getAllAccounts

    //updateAccount

    //getAccountById



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
