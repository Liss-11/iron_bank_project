package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.enums.StatusEnum;
import jakarta.persistence.Entity;
@Entity
public class Admin extends User{

    public Admin(){}

    public Admin(String username, String email, String password){
        super(username, email, password, RoleEnum.ROLE_ADMIN, StatusEnum.ACTIVE);
    }
}
