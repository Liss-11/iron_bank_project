package com.ironhack.iron_bank_project.accounts.service;

import com.ironhack.iron_bank_project.accounts.AccountValidateService;
import com.ironhack.iron_bank_project.accounts.dto.request.CheckingAccountCreateRequest;
import com.ironhack.iron_bank_project.accounts.dto.request.SavingAccountCreateRequest;
import com.ironhack.iron_bank_project.accounts.dto.response.AccountCreationResponse;
import com.ironhack.iron_bank_project.accounts.model.CheckingAccount;
import com.ironhack.iron_bank_project.accounts.model.SavingAccount;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterAdminRequest;
import com.ironhack.iron_bank_project.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountValidateService accountValidateService;

    public ResponseEntity<?> createCheckingAccount(CheckingAccountCreateRequest request) {
        var user1 = accountValidateService.isUserValid(request.getIdPrimaryOwner());
        User user2 = null;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        CheckingAccount account = CheckingAccount.checkingAccountFromDTO(request, user1, user2);
        account = accountRepository.save(account);
        return ResponseEntity.ok(AccountCreationResponse.fromAccount(account).toString());
    }

    public ResponseEntity<?> createSavingAccount(SavingAccountCreateRequest request) {
        var user1 = accountValidateService.isUserValid(request.getPrimaryOwnerId());
        User user2 = null;
        if (request.getSecondaryOwnerId() != null){
            user2 = accountValidateService.isUserValid(request.getSecondaryOwnerId());
        }
        SavingAccount account = SavingAccount.savingAccountFromDTO(request);

        return null;
    }

    public ResponseEntity<?> createCreditCard(RegisterAdminRequest request) {
        return null;
    }


    //create account


    //update account

    //delete account

    //change status account

    //lo llamo para cada account despues de cualquier ransaccion (en que esté involucrado) o cuando accedo  un account

    //TODO: llamar al autoUpdateAccount -> antes de cda transaccion
}
