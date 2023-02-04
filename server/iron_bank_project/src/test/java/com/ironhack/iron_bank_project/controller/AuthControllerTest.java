package com.ironhack.iron_bank_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.security.SecurityConfig;
import com.ironhack.iron_bank_project.security.UserDetailsImpl;
import com.ironhack.iron_bank_project.security.UserDetailsServiceImpl;
import com.ironhack.iron_bank_project.security.jwt.JwtAuthEntryPoint;
import com.ironhack.iron_bank_project.security.jwt.JwtService;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterAdminRequest;
import com.ironhack.iron_bank_project.users.model.Admin;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.users.repository.UserRepository;
import com.ironhack.iron_bank_project.users.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static com.ironhack.iron_bank_project.enums.Role.ROLE_ADMIN;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@WebMvcTest(AuthController.class)
@Import({SecurityConfig.class, UserDetailsServiceImpl.class, UserRepository.class, JwtAuthEntryPoint.class})
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository userRepository;

    @Autowired
    ObjectMapper om;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    AuthenticationService authenticationService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;




    @BeforeEach
    void setUp() {
      /*  Authentication auth = new UsernamePasswordAuthenticationToken("frolova.alissia@gmail.com", "Mushu0311");
        when(authenticationManager.authenticate(auth)).thenReturn(auth);*/

        //TODO crear un token propio -> u que lo devuelva el checker de codigo
        //TODO -> que el toquen que paso -> siempre sea devuelto como true ??

        //extraer el tokey y pasarlo en autentificacion


    }

    @Test
    void registerCustomer() {
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void registerAdmin() throws Exception {
        var registerAdminRequest = new RegisterAdminRequest(
                "alissia",
                "admin@gmail.com",
                "Mushu0311"
        );

        User admin = new Admin(
            "alissia",
            "admin@gmail.com",
            "Mushu0311"
        );
        admin.setRole(ROLE_ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setPasswordResetQuestion(null);
        admin.setPasswordResetAnswer(null);


        when(userRepository.save(admin)).thenReturn(admin);
        mockMvc.perform(post("/api/auth/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(registerAdminRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
             //   .andExpect(content().string(containsString("Admin registered successfully!")));
    }

    @Test
    void authenticate() {
    }

    @Test
    void logoutUser() {
    }

    @Test
    void forgotPassword() {
    }

    @Test
    void resetPassword() {
    }
}