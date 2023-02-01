package com.ironhack.iron_bank_project.accounts;

import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountValidateService {

    private final AccountRepository accountRepository;

    private final Validator validator;


    public User isUserValid(String userId){
        return validator.isUserValid(userId);
    }



 /*   public boolean autoUpdateAccount(Account account){
        switch (account.getAccountType()){
            case CREDIT -> accountValidateService.creditAccountValidate();
        }
        return false;
    }


}*/



}
