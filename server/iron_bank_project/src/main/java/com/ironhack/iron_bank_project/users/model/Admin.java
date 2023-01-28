package com.ironhack.iron_bank_project.users.model;

import com.ironhack.iron_bank_project.dtos.dtoAuthentication.request.RegisterAdminRequest;
import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.enums.StatusEnum;
import jakarta.persistence.Entity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
public class Admin extends User {

    public Admin(){}

    public Admin(String username, String email, String password){
        super(username, email, password, RoleEnum.ROLE_ADMIN, StatusEnum.ACTIVE);
    }

    public static Admin fromRegisterAdminRequest(RegisterAdminRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new Admin(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
    }
}
