package com.ironhack.iron_bank_project.accounts;

import com.ironhack.iron_bank_project.accounts.dto.CheckingAccountCreateRequest;
import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountValidateService {

    private final AccountRepository accountRepository;

    private final Validator validator;

    public boolean isCreationRequestValid(CheckingAccountCreateRequest request) {
        //check user existence and status

        //check user age -> between 0 and 120 (custom error) ------- in less than 18 (custom error)
        return false;

    }



 /*   public boolean autoUpdateAccount(Account account){
        switch (account.getAccountType()){
            case CREDIT -> accountValidateService.creditAccountValidate();
        }
        return false;
    }


}*/



}
