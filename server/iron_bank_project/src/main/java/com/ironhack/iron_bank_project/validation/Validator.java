package com.ironhack.iron_bank_project.validation;

import com.ironhack.iron_bank_project.accounts.model.*;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.utils.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Validator {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final ThirdPartyRepository thirdPartyRepository;

    //TODO recibe las inyecciones de todos los repositorios para hacer los chequeos
    //TODO es inyectado en todos los validadores de clases para validar entre clases

    public Customer isUserValid(String userId){
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getRole() == Role.ROLE_USER){
            return (Customer) user;
        }
        throw new IllegalArgumentException ("User must be a Customer");
    }

    public boolean isUserStudent(Customer customer) {
        return customer.getDateOfBirth().plusYears(24L).isAfter(LocalDate.now());
    }

    public ResponseEntity<?> updateAccountsInfo(){
        List<Account> accounts = accountRepository.findAll();
        for(Account account : accounts){
            switch (account.getAccountType()){
                case CREDIT -> actualizeCreditAccount((CreditCardAccount)account);
                case SAVING -> actualizeSavingAccount((SavingAccount)account);
                case STUDENT -> actualizeStudentAccount((StudentCheckingAccount)account);
                case CHECKING -> actualizeCheckingAccount((CheckingAccount)account);
                default -> throw new RuntimeException("Something gone wrong. Nonexistent account type is used!");
            }
        }
        return ResponseEntity.ok("Now, all Accounts information is up to date!");
    }

    public void actualizeCheckingAccount(CheckingAccount account) {
        if(account.getMaintenanceFeeDate().isBefore(LocalDateTime.now())){
            account.setMaintenanceFeeDate(account.getMaintenanceFeeDate().plusMonths(1L));
            account.setBalance(new Money(account.getBalance().getAmount().subtract(account.getMonthlyMaintenanceFee())));
        }
    }

    public void actualizeStudentAccount(StudentCheckingAccount account) {}

    public void actualizeCreditAccount(CreditCardAccount account){
        var associatedAccount = accountRepository.findById(account.getAssociatedToAccount().getId());
        if (associatedAccount.isPresent()){
            if (associatedAccount.get().getStatus() == AccountStatus.ACTIVE){
                account.setStatus(AccountStatus.ACTIVE);
            }
            else{account.setStatus(AccountStatus.FROZEN);}
        }else{
            accountRepository.delete(account);
        }
        if(account.getInterestPaymentDate().isBefore(LocalDateTime.now())){
            account.setInterestPaymentDate(account.getInterestPaymentDate().plusMonths(1L));
            BigDecimal checkingAccountBalance = associatedAccount.get().getBalance().getAmount();
            BigDecimal creditAccountBalance = account.getBalance().getAmount();
            BigDecimal amountToSubstract = (creditAccountBalance.multiply(account.getInterestRate())).add(creditAccountBalance);
            associatedAccount.get().setBalance(new Money(checkingAccountBalance.subtract(amountToSubstract)));
            account.setBalance(new Money(BigDecimal.valueOf(0.0)));
        }
    }

    public void actualizeSavingAccount(SavingAccount account){
        if(account.getInterestPaymentDate().isBefore(LocalDateTime.now())){
            account.setInterestPaymentDate(account.getInterestPaymentDate().plusYears(1L));
            BigDecimal savingAccountBalance = account.getBalance().getAmount();
            BigDecimal interestFromSavingAccount = savingAccountBalance.multiply(account.getInterestRate());
            account.setBalance(new Money(savingAccountBalance.add(interestFromSavingAccount)));
        }

    }

}
