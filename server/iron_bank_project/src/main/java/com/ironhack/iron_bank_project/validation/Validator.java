package com.ironhack.iron_bank_project.validation;

import com.ironhack.iron_bank_project.accounts.repository.AccountRepository;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Validator {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final ThirdPartyRepository thirdPartyRepository;

    //TODO recibe las inyecciones de todos los repositorios para hacer los chequeos
    //TODO es inyectado en todos los validadores de clases para validar entre clases

    public User isUserValid(String userId){
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}
