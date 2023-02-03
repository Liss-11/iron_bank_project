package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.transactions.Transaction;
import com.ironhack.iron_bank_project.transactions.TransactionService;
import com.ironhack.iron_bank_project.transactions.dtos.request.TransferRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> makeTransfer(@Valid @RequestBody TransferRequest transfer){
        return transactionService.makeTransfer(transfer);

    }



}
