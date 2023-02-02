package com.ironhack.iron_bank_project.validation;

import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
}
