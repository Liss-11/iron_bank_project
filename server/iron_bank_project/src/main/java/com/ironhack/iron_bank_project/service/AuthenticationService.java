package com.ironhack.iron_bank_project.service;

import com.ironhack.iron_bank_project.dtoRequest.AuthenticationRequest;
import com.ironhack.iron_bank_project.dtoRequest.RegisterRequest;
import com.ironhack.iron_bank_project.dtoResponse.AuthenticationResponse;
import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.exception.CustomerWithEmailAlreadyExistsException;
import com.ironhack.iron_bank_project.model.Customer;
import com.ironhack.iron_bank_project.model.CustomersToAdd;
import com.ironhack.iron_bank_project.model.User;
import com.ironhack.iron_bank_project.repository.CustomersToAddRepository;
import com.ironhack.iron_bank_project.repository.UserRepository;
import com.ironhack.iron_bank_project.security.UserDetailsImpl;
import com.ironhack.iron_bank_project.security.jwt.JwtService;
import com.ironhack.iron_bank_project.utils.Address;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    private final CustomersToAddRepository customersToAddRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final ModelMapper modelMapper = new ModelMapper();


    PropertyMap<RegisterRequest, Customer> customerMapping = new PropertyMap<>() {
        protected void configure() {
            map().setPrimaryAddress(
                    new Address(source.getStreet(), source.getCity(), source.getPostalCode(), source.getCountry()));
            map().setDateOfBirth(LocalDate.parse(source.getDateOfBirth()));
            map().setRole(RoleEnum.USER);
        }
    };


    //I want to map the registerRequest to a new Customer - see if there is a customer with this email
    //after save the customer into a new table - customers_to_add -> saving the statusENUM as PENDENT
 /*   public AuthenticationResponse register(RegisterRequest request) {

        var user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            throw new CustomerWithEmailAlreadyExistsException();
        }

        TypeMap<RegisterRequest, Customer> typeMap = modelMapper.getTypeMap(RegisterRequest.class, Customer.class);
        if (typeMap == null) { // if not  already added
            modelMapper.addMappings(customerMapping);
        }
        var customer = modelMapper.map(request, Customer.class);
        customersToAddRepository.save(modelMapper.map(customer, CustomersToAdd.class));

        var jwtToken = jwtService.generateTokenFromUsername(new UserDetailsImpl(customer));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }*/


    public AuthenticationResponse register(RegisterRequest request) {

        var userExists = userRepository.findByEmail(request.getEmail());
        if(userExists.isPresent()){
            throw new CustomerWithEmailAlreadyExistsException();
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(RoleEnum.USER);

        UserDetails userDetails = new UserDetailsImpl(user);

        TypeMap<RegisterRequest, Customer> typeMap = modelMapper.getTypeMap(RegisterRequest.class, Customer.class);
        if (typeMap == null) { // if not  already added
            modelMapper.addMappings(customerMapping);
        }
        var customer = modelMapper.map(request, Customer.class);

        customersToAddRepository.save(modelMapper.map(customer, CustomersToAdd.class));

        var jwtToken = jwtService.generateTokenFromUsername(userDetails);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



 /*   public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateTokenFromUsername(new UserDetailsImpl(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }*/


   // Another option

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(request.getEmail());
        if(user == null){
            throw new UsernameNotFoundException("This User doesn't exist");
        }
        var jwtToken = jwtService.generateTokenFromUsername(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
