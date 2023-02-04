package com.ironhack.iron_bank_project.users.service;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterThirdPartyRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.respons.RegisterThirdPartyResponse;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.exception.UserWithEmailAlreadyExistsException;
import com.ironhack.iron_bank_project.users.model.ThirdParty;
import com.ironhack.iron_bank_project.users.repository.ThirdPartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThirdPartyService {

    private final ThirdPartyRepository thirdPartyRepository;

    public ResponseEntity<?> registerThirdParty(RegisterThirdPartyRequest request) {

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_USER"))){
            return ResponseEntity.badRequest().body("You must SignOut in order to register a new ThirdParty User");
        }

        if (thirdPartyRepository.existsByEmail(request.getEmail())){
            throw new UserWithEmailAlreadyExistsException();
        }
        if (thirdPartyRepository.existsByUsername(request.getUsername())) {
            throw new UserWithEmailAlreadyExistsException("User with this username Already exists");
        }

        ThirdParty entity = ThirdParty.fromRegisterThirdPartyRequest(request);

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            entity.setStatus(UserStatus.ACTIVE);
        }

        thirdPartyRepository.save(entity);

        var response = new RegisterThirdPartyResponse(entity.getHashedKey());

        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString());
    }

    public ResponseEntity<?> deleteById(Long id) {
        thirdPartyRepository.findById(id).orElseThrow(UserNotFoundException::new);
        thirdPartyRepository.deleteById(id);
        return ResponseEntity.ok("User deleted Successfully!");
    }
}
