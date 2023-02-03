package com.ironhack.iron_bank_project.transactions;

import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.enums.TransactionType;
import com.ironhack.iron_bank_project.transactions.dtos.request.TransferRequest;
import com.ironhack.iron_bank_project.transactions.dtos.response.TransactionResponse;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.utils.Money;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final Validator validator;


    @Transactional
    public ResponseEntity<?> makeTransfer(TransferRequest transfer) {
        User user = validator.loggedUser();
        Account accountFrom = validator.isAccountValidForTransfers(transfer.getFromAccountId());
        Account accountTo = validator.isAccountValidForTransfers(transfer.getToAccountId());
        if (user.getRole() == Role.ROLE_USER) {
            validator.isUserOwnerOfAccount(user, accountFrom);
        }
        validator.isTransferToItself(transfer.getFromAccountId(), transfer.getToAccountId());
        validator.accountHasEnoughMoney(accountFrom, transfer.getAmount());
        accountFrom.setBalance(new Money(accountFrom.getBalance().decreaseAmount(transfer.getAmount())));
        accountTo.setBalance(new Money(accountTo.getBalance().increaseAmount(transfer.getAmount())));
        Transaction transaction = new Transaction(accountFrom, accountTo, new Money(transfer.getAmount()), TransactionType.TRANSFER, transfer.getDescription());
        transactionRepository.save(transaction);
        TransactionResponse response = new TransactionResponse(
                transaction.getCreationInstant(),
                transfer.getToAccountId(),
                transaction.getType(),
                transaction.getAmount().getAmount(),
                transfer.getFromAccountId(),
                accountFrom.getBalance().getAmount());
        return ResponseEntity.ok(response);
    }
}
