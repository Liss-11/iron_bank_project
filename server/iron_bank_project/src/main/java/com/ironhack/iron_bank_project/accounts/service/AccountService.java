package com.ironhack.iron_bank_project.accounts.service;

import com.ironhack.iron_bank_project.accounts.AccountValidateService;
import com.ironhack.iron_bank_project.accounts.dto.request.ChangeAccountStatusRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.CreateCheckingAccountRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.CreateCreditCardAccountRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.CreateSavingAccountRequest;
import com.ironhack.iron_bank_project.accounts.dto.response.AccountCreationResponse;
import com.ironhack.iron_bank_project.accounts.model.CheckingAccount;
import com.ironhack.iron_bank_project.accounts.model.CreditCardAccount;
import com.ironhack.iron_bank_project.accounts.model.SavingAccount;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
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
        var user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        User user2 = null;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        CheckingAccount account = CheckingAccount.checkingAccountFromDTO(request, user1, user2);
        account = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreationResponse.fromAccount(account).toString());
    }

    public ResponseEntity<?> createSavingAccount(CreateSavingAccountRequest request) {
        var user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        User user2 = null;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        SavingAccount account = SavingAccount.savingAccountFromDTO(request,user1, user2);
        account = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreationResponse.fromAccount(account).toString());
    }

    public ResponseEntity<?> createCreditCard(CreateCreditCardAccountRequest request) {
        var user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        User user2 = null;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        CreditCardAccount account = CreditCardAccount.fromDto(request, user1, user2);
        account = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreationResponse.fromAccount(account).toString());
    }

    public ResponseEntity<?> deleteAccountById(Long id) {
        accountRepository.findById(id).orElseThrow(UserNotFoundException::new);
        accountRepository.deleteById(id);
        return ResponseEntity.ok("Account deleted Successfully!");
    }

  /*  public ResponseEntity<?> updateAccount(Long id, UpdateCheckingAccountRequest request) {
        var user = userRepository.findById(id);
        if(user.isPresent()){
            var customer = (Customer)user.get();
            if(request.getName() != null){customer.setName(request.getName());}
            if(request.getEmail() != null){customer.setEmail(request.getEmail());}
            if(request.getPassword() != null){customer.setPassword(request.getPassword());}
            if(request.getDateOfBirth() != null){customer.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));}
            if(request.getPassword() != null){customer.setPassword(passwordEncoder.encode(request.getEmail()));}
            Address address = customer.getPrimaryAddress();
            if(request.getStreet() != null){address.setStreet(request.getStreet());}
            if(request.getCity() != null){address.setCity(request.getCity());}
            if(request.getPostalCode() != null){address.setPostalCode(request.getPostalCode());}
            if(request.getCountry() != null){address.setCountry(request.getCountry());}
            customer.setPrimaryAddress(address);
            userRepository.save(customer);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The patientId doesn't exist.");
        }
        return ResponseEntity.ok("The account is successfully Updated");
    }*/

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

}
