package com.ironhack.iron_bank_project.users.service;

import com.ironhack.iron_bank_project.enums.StatusEnum;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    public ResponseEntity<?> deleteUserById(String id) {
        var user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted Successfully!");
    }

    public ResponseEntity<?> deleteActualUser() {
        var userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail);
            if (user.isPresent()) {
                user.get().setStatus(StatusEnum.DELETED);
                userRepository.save(user.get());
            } else {
                throw new UserNotFoundException();
            }
            authenticationService.logout();
            return ResponseEntity.ok("User deleted Successfully!");
        }
        return ResponseEntity.badRequest().body("Incorrect credentials");
    }
}
