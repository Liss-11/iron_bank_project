package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.transactions.Transaction;
import com.ironhack.iron_bank_project.transactions.TransactionService;
import com.ironhack.iron_bank_project.transactions.dtos.request.TransferRequest;
import com.ironhack.iron_bank_project.transactions.dtos.request.WithdrawDepositRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody TransferRequest transfer){
        return transactionService.makeTransfer(transfer);
    }

    @PostMapping("/withdraw")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> makeWithdraw(@Valid @RequestBody WithdrawDepositRequest withdraw){
        return transactionService.makeWithdraw(withdraw);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> makeDeposit(@Valid @RequestBody WithdrawDepositRequest deposit){
        return transactionService.makeDeposit(deposit);
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> makePurchase(@Valid @RequestBody WithdrawDepositRequest purchase){
        return transactionService.makePurchase(purchase);
    }



}
