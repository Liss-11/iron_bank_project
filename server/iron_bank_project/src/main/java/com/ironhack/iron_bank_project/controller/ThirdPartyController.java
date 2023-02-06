package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.transactions.TransactionService;
import com.ironhack.iron_bank_project.transactions.dtos.request.ThirdPartyTransactionRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterThirdPartyRequest;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.service.ThirdPartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ThirdPartyController {

    private final ThirdPartyService thirdPartyService;

    private final TransactionService transactionService;

    private final ThirdPartyRepository thirdPartyRepository;

    @GetMapping("/admin/third_party/all_accounts")
    public ResponseEntity<?> getAllThirdParties(){
        return ResponseEntity.ok(thirdPartyRepository.findAll());
    }

    @PostMapping("/admin/third_party/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerThirdParty(@Valid @RequestBody RegisterThirdPartyRequest request){
        return thirdPartyService.registerThirdParty(request);
    }

    @DeleteMapping("/admin/third_party/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteThirdParty(@PathVariable (name = "id") Long id){
        return thirdPartyService.deleteById(id);
    }

    @PostMapping("/third_party/withdraw")
    public ResponseEntity<?> thirdPartyWithdraw(@Valid @RequestBody ThirdPartyTransactionRequest request,
                                                @RequestHeader (name="Authorization") String hashedKey){
        return transactionService.thirdPartyWithdraw(request, hashedKey);
    }

    @PostMapping("/third_party/deposit")
    public ResponseEntity<?> thirdPartyDeposit(@Valid @RequestBody ThirdPartyTransactionRequest request,
                                                @RequestHeader (name="Authorization") String hashedKey){
        return transactionService.thirdPartyDeposit(request, hashedKey);
    }

}
