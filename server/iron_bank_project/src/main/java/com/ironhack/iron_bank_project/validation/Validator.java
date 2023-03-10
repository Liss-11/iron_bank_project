package com.ironhack.iron_bank_project.validation;

import com.ironhack.iron_bank_project.accounts.model.*;
import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.exception.NotEnoughMoneyInAccountException;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.transactions.dtos.request.ThirdPartyTransactionRequest;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.ThirdParty;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.utils.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
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


    public Customer isUserCustomer(String userId){
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getRole() == Role.ROLE_USER){
            return (Customer) user;
        }
        throw new IllegalArgumentException ("User/s must be a Customer");
    }

    public boolean isUserActive(String userId){
        var user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (user.getStatus() != UserStatus.ACTIVE){
            throw new IllegalArgumentException ("User has Status: " + user.getStatus().toString() + "\nOnly ACTIVE users can use theis accounts");
        }
        return true;
    }

    public Account isAccountValid(Long accountId) {
        var account = accountRepository.findById(accountId);
        if (account.isPresent()){
            if(!isUserActive(account.get().getPrimaryOwner().getId())){
                throw new IllegalArgumentException ("User of account with id: " + accountId + " is not an ACTIVE user. His accounts are BLOCKED");
            }
            if(account.get().getStatus() != AccountStatus.ACTIVE && account.get().getStatus() != AccountStatus.EMPTY){
                throw new IllegalArgumentException("Account with id: " + accountId + " has Status: " + account.get().getStatus() + ". ONLY accounts with status ACTIVE can make transfers");
            }
            return account.get();
        }
        throw new NoSuchElementException("Account with id: " + accountId + " doesn't exist!");
    }


    public boolean isUserStudent(Customer customer) {
        return customer.getDateOfBirth().plusYears(24L).isAfter(LocalDate.now());
    }


    @Scheduled(cron = "0 0 */23 * * ?")
    public void updateInfo(){
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
        System.out.println("Accounts up do date");
    }


    public void actualizeCheckingAccount(CheckingAccount account) {
        if(account.getMaintenanceFeeDate().isBefore(LocalDateTime.now())){
           // account.setMaintenanceFeeDate(account.getMaintenanceFeeDate().plusSeconds(30L));
            account.setBalance(new Money(account.getBalance().getAmount().subtract(account.getMonthlyMaintenanceFee())));
            accountRepository.save(account);
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
            accountRepository.save(account);
        }
    }

    public void actualizeSavingAccount(SavingAccount account){
        if(account.getInterestPaymentDate().isBefore(LocalDateTime.now())){
            account.setInterestPaymentDate(account.getInterestPaymentDate().plusYears(1L));
            BigDecimal savingAccountBalance = account.getBalance().getAmount();
            BigDecimal interestFromSavingAccount = savingAccountBalance.multiply(account.getInterestRate());
            account.setBalance(new Money(savingAccountBalance.add(interestFromSavingAccount)));
            accountRepository.save(account);
        }

    }

    public User loggedUser() {
        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userEmail != null){
            return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        }else{
            throw new NoSuchElementException("There is no user logged in this moment");
        }
    }


    public void isUserOwnerOfAccount(User user, Account accountFrom) {
        if(!accountFrom.getPrimaryOwner().equals(user)){
            throw new IllegalArgumentException("The account with id: " + accountFrom.getId() + " doesn't belong to the logged Customer");
        }
    }

    public void accountHasEnoughMoney(Account accountFrom, BigDecimal amount) {
        if (accountFrom.getBalance().getAmount().compareTo(amount) < 0){
            throw new NotEnoughMoneyInAccountException("The account with id: " + accountFrom.getId() + " has not enough money. Actual amount: " + accountFrom.getBalance().getAmount());
        }
    }

    public void isTransferToItself(Long formId, Long toId) {
        if(formId.equals(toId)){
            throw new IllegalArgumentException("For transfer you have to introduce two DIFFERENT accounts");
        }
    }

    public boolean isCheckingAccount(Account account) {
        if( account.getAccountType() == AccountType.CHECKING || account.getAccountType() == AccountType.STUDENT){
            return true;
        }
        throw new IllegalArgumentException("Only Checking and Students accounts can receive transferred money");
    }

    public boolean isCheckingAccountOrCredit(Account account) {
        if(account.getAccountType() == AccountType.CHECKING || account.getAccountType() == AccountType.STUDENT || account.getAccountType() == AccountType.CREDIT){
            return true;
        }
        throw new IllegalArgumentException("Saving accounts can't transfer money");
    }

    public void isThirdPartyValid(String username, String hashedKey) {
        Optional<ThirdParty> entity = thirdPartyRepository.findByUsername(username);
        if(entity.isPresent()){
            if(!entity.get().getHashedKey().equals(hashedKey)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect HashedKey");
            }
            if(entity.get().getStatus() != UserStatus.ACTIVE){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your status is not ACTIVE");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "There is no thirdParty registered with this username");

        }
    }


}
