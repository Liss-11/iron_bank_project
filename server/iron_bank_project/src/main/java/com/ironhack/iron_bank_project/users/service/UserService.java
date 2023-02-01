package com.ironhack.iron_bank_project.users.service;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.ChangeStatusRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.UpdateCustomerRequest;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.exception.UserNotFoundException;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.utils.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;

    private final PasswordEncoder passwordEncoder;

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
                user.get().setStatus(UserStatus.DELETED);
                userRepository.save(user.get());
            } else {
                throw new UserNotFoundException();
            }
            authenticationService.logout();
            return ResponseEntity.ok("User deleted Successfully!");
        }
        return ResponseEntity.badRequest().body("Incorrect credentials");
    }

    public ResponseEntity<?> updateUser(String id, UpdateCustomerRequest request) {
        var user = userRepository.findById(id);
        if(user.isPresent()){
            var customer = (Customer)user.get();
            if(request.getName() != null){customer.setName(request.getName());}
            if(request.getEmail() != null){customer.setEmail(request.getEmail());}
            if(request.getPassword() != null){customer.setPassword(request.getPassword());}
            if(request.getDateOfBirth() != null){customer.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));}
            if(request.getPassword() != null){customer.setPassword(passwordEncoder.encode(request.getEmail()));}
             Address address = customer.getPrimaryAddress();
            if(request.getStreet() != null){address.setStreet(request.getStreet());}
            if(request.getCity() != null){address.setCity(request.getCity());}
            if(request.getPostalCode() != null){address.setPostalCode(request.getPostalCode());}
            if(request.getCountry() != null){address.setCountry(request.getCountry());}
            customer.setPrimaryAddress(address);
            userRepository.save(customer);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The patientId doesn't exist.");
        }
        return ResponseEntity.ok("The account is successfully Updated");
    }

    public ResponseEntity<?> changeStatus(String id, ChangeStatusRequest request) {
        var user = userRepository.findById(id);
        if(user.isPresent()){
            try{
                user.get().setStatus(request.getUserStatus());
                userRepository.save(user.get());
            }catch(Exception e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status value not valid.");
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The employeeId doesn't exist.");
        }
        return ResponseEntity.ok("Status is updated!");
    }
}
