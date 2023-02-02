package com.ironhack.iron_bank_project.accounts;

import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountValidateService {

    private final AccountRepository accountRepository;

    private final Validator validator;


    public Customer isUserValid(String userId){
        return validator.isUserValid(userId);
    }

    public boolean isUserStudent(Customer customer){return validator.isUserStudent(customer);};

    public Account isValidUserAccount(Customer user1, long associatedToAccountId) {
        List<Account> accounts = user1.getMyAccounts();
        if(accounts.isEmpty()){
            throw new NoSuchElementException("User has no account\n");
        }
        Account myAccount = null;
        for(Account account : accounts){
            if(account.getId().equals(associatedToAccountId)){
                myAccount = account;
            }
        }
        if(myAccount == null){throw new IllegalArgumentException("This account doesn't exist or doesn't belong to this user");}
        if(myAccount.getAccountType() == AccountType.CHECKING || myAccount.getAccountType() == AccountType.STUDENT){
            if(myAccount.getStatus() != AccountStatus.ACTIVE){
                throw new IllegalArgumentException("For associate a Credit Card to this account, the account must be ACTIVE");
            }
            if(myAccount.getCreditCard() != null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account already has an associated CreditCard\nYou can have only one CreditCard per account");
            }
            return myAccount;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The associated account must be a Checking (Student) Account");
    }



 /*   public boolean autoUpdateAccount(Account account){
        switch (account.getAccountType()){
            case CREDIT -> accountValidateService.creditAccountValidate();
        }
        return false;
    }


}*/



}
