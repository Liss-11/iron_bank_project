package com.ironhack.iron_bank_project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.iron_bank_project.security.SecurityConfig;
import com.ironhack.iron_bank_project.security.UserDetailsServiceImpl;
import com.ironhack.iron_bank_project.security.jwt.JwtAuthEntryPoint;
import com.ironhack.iron_bank_project.security.jwt.JwtService;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(AccountController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class, UserRepository.class, JwtAuthEntryPoint.class})
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Autowired
    ObjectMapper om;

    @MockBean
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;




    @BeforeEach
    void setUp() {
        Authentication auth = new UsernamePasswordAuthenticationToken("email", "password");
        when(authenticationManager.authenticate(auth)).thenReturn(auth);
       // when(roleRepository.findByName(RoleEnum.ROLE_ADMIN)).thenReturn(Optional.of(new Role(RoleEnum.ROLE_ADMIN)));
    }












    @AfterEach
    void tearDown() {
    }

    @Test
    void getAccountsLongVersion() {
    }

    @Test
    void getAccountsShortVersion() {
    }

    @Test
    void getMyAccounts() {
    }

    @Test
    void getAccountById() {
    }

    @Test
    void createCheckingAccount() {
    }

    @Test
    void createSavingAccount() {
    }

    @Test
    void createCreditCard() {
    }

    @Test
    void updateCheckingAccount() {
    }

    @Test
    void updateSavingAccount() {
    }

    @Test
    void updateCreditCardAccount() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void deleteAccountById() {
    }
}