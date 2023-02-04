package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.transactions.TransactionService;
import com.ironhack.iron_bank_project.transactions.dtos.request.ThirdPartyTransactionRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterThirdPartyRequest;
import com.ironhack.iron_bank_project.users.service.ThirdPartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/third-party")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    private final TransactionService transactionService;

    //TODO PUT
    //TODO GET

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerThirdParty(@Valid @RequestBody RegisterThirdPartyRequest request){
        return thirdPartyService.registerThirdParty(request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteThirdParty(@PathVariable (name = "id") Long id){
        return thirdPartyService.deleteById(id);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> thirdPartyWithdraw(@Valid @RequestBody ThirdPartyTransactionRequest request,
                                                @RequestHeader (name="Authorization") String hashedKey){
        return transactionService.thirdPartyWithdraw(request, hashedKey);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> thirdPartyDeposit(@Valid @RequestBody ThirdPartyTransactionRequest request,
                                                @RequestHeader (name="Authorization") String hashedKey){
        return transactionService.thirdPartyDeposit(request, hashedKey);
    }

}