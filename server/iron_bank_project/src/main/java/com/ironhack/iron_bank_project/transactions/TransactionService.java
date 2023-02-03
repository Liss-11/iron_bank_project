package com.ironhack.iron_bank_project.transactions;

import com.ironhack.iron_bank_project.transactions.dtos.request.TransferRequest;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final Validator validator;


    @Transactional
    public ResponseEntity<?> makeTransfer(TransferRequest transfer) {

        //TODO -> veure qui es l'usuario loguejat ( var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        //        if (userEmail != null) {
        //            var user = userRepository.findByEmail(userEmail);
        //            if (user.isPresent()) {
        //                user.get().setStatus(UserStatus.DELETED);
        //                userRepository.save(user.get());
        //            } else {
        //                throw new UserNotFoundException();
        //            }
        //            authenticationService.logout();
        //            return ResponseEntity.ok("User deleted Successfully!");
        //        }
        //        return ResponseEntity.badRequest().body("Incorrect credentials");)
        //User loggedUser =

        //ver si el logueado es user or admin -->
        //ADMIN (solo ver si account is valid): existe, es chcecking or student
        //USER -> account pertenece a este usuario(from)

        validator.isAccountValid(transfer.getFromAccountId());
        //ver si la cuenta existe

        //ver si la cuenta pertenece al usuario logueado

        //ver si la cuenta esta -> ACTIVE y el usuario -> ACTIVE

        //TODO -> esto para las dos cuentas

        //ver si la cuenta a la que intenta hacer la transacion tiene el dinero suficiente para hacerla


        //TODO si todo ok -> hacer la transferencia, actualizando los dos accounts.
        validator.
    }
}
