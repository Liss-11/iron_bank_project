package com.ironhack.iron_bank_project.users.model;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterThirdPartyRequest;
import com.ironhack.iron_bank_project.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Data
public class ThirdParty{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String hashedKey;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public ThirdParty(String username, String email, String password, UserStatus status) {
        setUsername(username);
        setEmail(email);
        setHashedKey(password);
        setStatus(status);
    }

    public ThirdParty() {
    }

    public static ThirdParty fromRegisterThirdPartyRequest(RegisterThirdPartyRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new ThirdParty(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), UserStatus.PENDENT);
    }


}
