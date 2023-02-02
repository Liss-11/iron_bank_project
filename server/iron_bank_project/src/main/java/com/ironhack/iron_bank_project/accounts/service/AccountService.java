package com.ironhack.iron_bank_project.accounts.service;

import com.ironhack.iron_bank_project.accounts.AccountValidateService;
import com.ironhack.iron_bank_project.accounts.dto.request.*;
import com.ironhack.iron_bank_project.accounts.dto.response.AccountCreationResponse;
import com.ironhack.iron_bank_project.accounts.model.*;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.ChangeStatusRequest;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.utils.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AccountService {

    //TODO: create the autoUpdateAccount
    //TODO: add updateAccount() (afler that Apply the autoUpdateAccount)
    //TODO: create updateAmount() (afler that Apply the autoUpdateAccount)
    //TODO: llamar al autoUpdateAccount -> antes de cda transaccion

    private final AccountRepository accountRepository;

    private final AccountValidateService accountValidateService;

    public ResponseEntity<?> createCheckingAccount(CreateCheckingAccountRequest request) {
        Customer user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        Customer user2 = null;
        Account account;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        //if user is less than 24 student account
        account = accountValidateService.isUserStudent(user1)?
                StudentCheckingAccount.fromDTO(request, user1, user2):
                CheckingAccount.fromDTO(request, user1, user2);
        account = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreationResponse.fromAccount(account).toString());
    }

    public ResponseEntity<?> createSavingAccount(CreateSavingAccountRequest request) {
        Customer user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        Customer user2 = null;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        SavingAccount account = SavingAccount.savingAccountFromDTO(request,user1, user2);
        account = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreationResponse.fromAccount(account).toString());
    }

    public ResponseEntity<?> createCreditCard(CreateCreditCardAccountRequest request) {
        Customer user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        Account associateToAccount = accountValidateService.isValidUserAccount(user1, request.getAssociatedToAccountId());
        if(associateToAccount != null) {
            Customer user2 = null;
            if (request.getSecondaryOwnerId() != null) {
                user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
            }
            CreditCardAccount account = CreditCardAccount.fromDto(request, user1, user2, associateToAccount);
            account = accountRepository.save(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreationResponse.fromAccount(account).toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You need first open a Checking Account\n");
    }

    public ResponseEntity<?> updateCheckingAccount(Long id, UpdateCheckingAccountRequest request) {
        var account = accountRepository.findById(id);
        if(account.isPresent()){
            Customer user = null;
            if(request.getSecondaryOwnerId() != null){user = accountValidateService.isUserValid(request.getSecondaryOwnerId());}
            if(account.get().getAccountType() == AccountType.CHECKING){
                CheckingAccount checking = CheckingAccount.updateFromDTO(request, (CheckingAccount)account.get(), user);
                accountRepository.save(checking);
            }else if(account.get().getAccountType() == AccountType.STUDENT){
                if(request.getMinimumBalance() != null){throw new IllegalArgumentException("StudentChecking Account hasn't Minimum Balance!");}
                StudentCheckingAccount checking = StudentCheckingAccount.updateFromDTO(request, (StudentCheckingAccount)account.get(), user);
                accountRepository.save(checking);
            }else{
                throw new IllegalArgumentException("This is not a Checking or StudentChecking Account");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account doesn't exist.");
        }
        return ResponseEntity.ok("The account is successfully Updated");
    }


    public ResponseEntity<?> changeStatus(Long id, ChangeAccountStatusRequest request) {
        var account = accountRepository.findById(id);
        if(account.isPresent()){
            try{
                account.get().setStatus(request.getAccountStatus());
                accountRepository.save(account.get());
            }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status value not valid.");
            }
            return ResponseEntity.ok("Status is updated!");
        }
        return ResponseEntity.badRequest().body("The accountId doesn't exist.");
    }

    public ResponseEntity<?> deleteAccountById(Long id) {
        accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        accountRepository.deleteById(id);
        return ResponseEntity.ok("Account deleted Successfully!");
    }


    //TODO
    public ResponseEntity<?> updateSavingAccount(Long id, UpdateSavingAccountRequest request) {
        return null;
    }

    //TODO
    public ResponseEntity<?> updateCreditCardAccount(Long id, UpdateCreditCardAccountRequest request) {
        return null;
    }
}
