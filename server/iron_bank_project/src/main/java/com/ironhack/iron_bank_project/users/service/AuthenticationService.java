package com.ironhack.iron_bank_project.users.service;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.AuthenticationRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterAdminRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.exception.UserWithEmailAlreadyExistsException;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.ResetPasswordRequest;
import com.ironhack.iron_bank_project.users.model.Admin;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.security.UserDetailsImpl;
import com.ironhack.iron_bank_project.security.jwt.JwtService;
import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final Validator validator;



   // public AuthenticationResponse registerCustomer(RegisterCustomerRequest request){
    public ResponseEntity<?> registerCustomer (RegisterCustomerRequest request){

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_USER"))){
            return ResponseEntity.badRequest().body("You must SignOut in order to create a New User");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserWithEmailAlreadyExistsException();
        }
        var customer = Customer.fromRegisterCustomerRequest(request);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            customer.setStatus(UserStatus.ACTIVE);
        }
        userRepository.save(customer);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Customer registered successfully!");
    }

    public ResponseEntity<?> registerAdmin(RegisterAdminRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserWithEmailAlreadyExistsException();
        }
        var customer = Admin.fromRegisterAdminRequest(request);
        userRepository.save(customer);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body("Admin registered successfully!");
    }

    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        if(user == null){
            throw new UsernameNotFoundException("This User doesn't exist");
        }
        var userIsActive = userRepository.findByEmail(request.getEmail());
        if(userIsActive.isPresent()){
            if(userIsActive.get().getStatus() != UserStatus.ACTIVE){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "This user actually is not ACTIVE");
            }
        }
        validator.updateAccountsInfo();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body("Welcome to your Account: [" + userDetails.getUsername() + "]");

    }


    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }


    public ResponseEntity<?> forgotPassword(String email) {
        var user = userRepository.findByEmail(email);
        if (user.isPresent()){
            return ResponseEntity.ok("Your question is: " + user.get().getPasswordResetQuestion());
        }else{
            throw new UsernameNotFoundException("No one user with this email in the database");
        }
    }

    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent()){
            if(!user.get().getPasswordResetAnswer().equals(request.getAnswer())){
                throw new IllegalArgumentException("Incorrect associated to account answer");
            }
            user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user.get());
            return ResponseEntity.ok("Your password is updated");

        }else{
            throw new UsernameNotFoundException("No one user with this email in the database");
        }
    }
}
