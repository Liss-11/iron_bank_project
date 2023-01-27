package com.ironhack.iron_bank_project.service;

import com.ironhack.iron_bank_project.dtoAuthentication.request.AuthenticationRequest;
import com.ironhack.iron_bank_project.dtoAuthentication.request.RegisterAdminRequest;
import com.ironhack.iron_bank_project.dtoAuthentication.request.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.enums.StatusEnum;
import com.ironhack.iron_bank_project.exception.CustomerWithEmailAlreadyExistsException;
import com.ironhack.iron_bank_project.model.Admin;
import com.ironhack.iron_bank_project.model.Customer;
import com.ironhack.iron_bank_project.repository.UserRepository;
import com.ironhack.iron_bank_project.security.UserDetailsImpl;
import com.ironhack.iron_bank_project.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;



   // public AuthenticationResponse registerCustomer(RegisterCustomerRequest request){
    public ResponseEntity<?> registerCustomer (RegisterCustomerRequest request){

        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_USER")) ||
                SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                        .contains(new SimpleGrantedAuthority("ROLE_THIRDPARTY"))){
            return ResponseEntity.badRequest().body("You must SignOut in order to create a New User");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomerWithEmailAlreadyExistsException();
        }
        var customer = Customer.fromRegisterCustomerRequest(request);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            customer.setStatus(StatusEnum.ACTIVE);
        }
        userRepository.save(customer);

        return ResponseEntity.ok("User registered successfully!");
    }

    public ResponseEntity<?> registerAdmin(RegisterAdminRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomerWithEmailAlreadyExistsException();
        }
        var customer = Admin.fromRegisterAdminRequest(request);
        userRepository.save(customer);

        return ResponseEntity.ok("Admin registered successfully!");
    }


    public ResponseEntity<?> authenticate(AuthenticationRequest request) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        if(user == null){
            throw new UsernameNotFoundException("This User doesn't exist");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtService.generateJwtCookie(userDetails);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(userDetails.getUsername());

    }


    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtService.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }


}
