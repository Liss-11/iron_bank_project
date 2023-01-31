package com.ironhack.iron_bank_project.accounts.service;

import com.ironhack.iron_bank_project.accounts.AccountValidateService;
import com.ironhack.iron_bank_project.accounts.dto.CheckingAccountCreateRequest;
import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.accounts.model.CheckingAccount;
import com.ironhack.iron_bank_project.accounts.model.StudentCheckingAccount;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.exception.AccountCanNotBeCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountValidateService accountValidateService;

    //desde aqui llamamos al accountValidate

    public Account createAccount(CheckingAccountCreateRequest request){
        if(accountValidateService.isCreationRequestValid(request)){
          /*  if(accountValidateService.isStudentAccount()){
                Account account = StudentCheckingAccount.studentAccountFromDTO(request);
                return accountRepository.save(account);
            }
            Account account = CheckingAccount.checkingAccountFromDto();
            return accountRepository.save(account);*/
            //comprobar la edad del usuario y mandar a crearse el account pertinente
        }else{
            throw new AccountCanNotBeCreated();
        }
        return null;
    }


    //create account


    //update account

    //delete account

    //change status account

    //lo llamo para cada account despues de cualquier ransaccion (en que esté involucrado) o cuando accedo  un account

    //TODO: llamar al autoUpdateAccount -> antes de cda transaccion
}
