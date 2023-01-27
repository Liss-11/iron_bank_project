package com.ironhack.iron_bank_project.service;

import com.ironhack.iron_bank_project.dtoRequest.AuthenticationRequest;
import com.ironhack.iron_bank_project.dtoRequest.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.dtoResponse.AuthenticationResponse;
import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.enums.StatusEnum;
import com.ironhack.iron_bank_project.exception.CustomerWithEmailAlreadyExistsException;
import com.ironhack.iron_bank_project.model.Customer;
import com.ironhack.iron_bank_project.model.User;
import com.ironhack.iron_bank_project.repository.UserRepository;
import com.ironhack.iron_bank_project.security.UserDetailsImpl;
import com.ironhack.iron_bank_project.security.jwt.JwtService;
import com.ironhack.iron_bank_project.utils.Address;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;



   // public AuthenticationResponse registerCustomer(RegisterCustomerRequest request){
    public ResponseEntity<?> registerCustomer (RegisterCustomerRequest request){

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomerWithEmailAlreadyExistsException();
        }

        var customer = Customer.fromRegisterCustomerRequest(request);
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            customer.setStatus(StatusEnum.ACTIVE);
        }

        System.out.println(customer.toString());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        UserDetails userDetails = UserDetailsImpl.build((User) customer);

        System.out.println(userDetails.getUsername());

        userRepository.save(customer);

        //var jwtToken = jwtService.generateTokenFromUsername(userDetails);

        return ResponseEntity.ok("User registered successfully!");


        //return new AuthenticationResponse(jwtToken);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        if(user == null){
            throw new UsernameNotFoundException("This User doesn't exist");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        System.out.println(userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        var jwtToken = jwtService.generateTokenFromUsername(user);

        return new AuthenticationResponse(jwtToken);

    }

    /*
    *  Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));*/

}
