package com.ironhack.iron_bank_project.transactions;

import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.enums.TransactionType;
import com.ironhack.iron_bank_project.transactions.dtos.request.TransferRequest;
import com.ironhack.iron_bank_project.transactions.dtos.request.WithdrawDepositRequest;
import com.ironhack.iron_bank_project.transactions.dtos.response.DepositResponse;
import com.ironhack.iron_bank_project.transactions.dtos.response.TransactionResponse;
import com.ironhack.iron_bank_project.transactions.dtos.response.WithdrawResponse;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.utils.Money;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final Validator validator;


    @Transactional
    public ResponseEntity<?> makeTransfer(TransferRequest transfer) {
        User user = validator.loggedUser();
        Account accountFrom = validator.isAccountValid(transfer.getFromAccountId());
        Account accountTo = validator.isAccountValid(transfer.getToAccountId());
        if(validator.isCheckingAccount(accountFrom) && validator.isCheckingAccount(accountTo)) {
            if (user.getRole() == Role.ROLE_USER) {
                validator.isUserOwnerOfAccount(user, accountFrom);
            }
            validator.isTransferToItself(transfer.getFromAccountId(), transfer.getToAccountId());
            validator.accountHasEnoughMoney(accountFrom, transfer.getAmount());
            BigDecimal initialAmount = accountFrom.getBalance().getAmount();
            accountFrom.setBalance(new Money(accountFrom.getBalance().decreaseAmount(transfer.getAmount())));
            accountTo.setBalance(new Money(accountTo.getBalance().increaseAmount(transfer.getAmount())));
            Transaction transaction = new Transaction(accountFrom, accountTo, new Money(transfer.getAmount()), TransactionType.TRANSFER, transfer.getDescription());
            transactionRepository.save(transaction);
            TransactionResponse response = new TransactionResponse(
                    transaction.getCreationInstant(),
                    accountFrom.getId(),
                    initialAmount,
                    transaction.getType(),
                    accountTo.getId(),
                    transfer.getAmount(),
                    accountFrom.getBalance().getAmount());
            return ResponseEntity.ok(response);
        }
        throw new IllegalArgumentException("Both accounts must be CheckerAccount or StudentAccounts");
    }


    @Transactional
    public ResponseEntity<?> makeWithdraw(WithdrawDepositRequest withdraw) {
        User user = validator.loggedUser();
        Account accountFrom = validator.isAccountValid(withdraw.getAccountId());
        if (user.getRole() == Role.ROLE_USER) {
            validator.isUserOwnerOfAccount(user, accountFrom);
        }
        validator.accountHasEnoughMoney(accountFrom, withdraw.getAmount());
        BigDecimal initialAmount = accountFrom.getBalance().getAmount();
        accountFrom.setBalance(new Money(accountFrom.getBalance().decreaseAmount(withdraw.getAmount())));
        Transaction transaction = new Transaction(accountFrom, null, new Money(withdraw.getAmount()), TransactionType.WITHDRAW, withdraw.getDescription());
        transactionRepository.save(transaction);
        WithdrawResponse response = new WithdrawResponse(
                transaction.getCreationInstant(),
                transaction.getFrom().getId(),
                initialAmount,
                transaction.getType(),
                withdraw.getAmount(),
                accountFrom.getBalance().getAmount()
        );
        return ResponseEntity.ok(response);
    }

    @Transactional
    public ResponseEntity<?> makeDeposit(WithdrawDepositRequest deposit) {
        Account accountTo = validator.isAccountValid(deposit.getAccountId());
        if(accountTo.getAccountType() != AccountType.CREDIT) {
            accountTo.setBalance(new Money(accountTo.getBalance().increaseAmount(deposit.getAmount())));
            Transaction transaction = new Transaction((Account) null, accountTo, new Money(deposit.getAmount()), TransactionType.DEPOSIT, deposit.getDescription());
            transactionRepository.save(transaction);
            DepositResponse response = new DepositResponse(
                    transaction.getCreationInstant(),
                    transaction.getTo().getId(),
                    deposit.getAmount()
            );
            return ResponseEntity.ok(response);
        }
        throw new IllegalArgumentException("CreditCardAccounts can't receive Deposits");
    }
}
