package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Admin extends User{

    public Admin(){}

    public Admin(String username, String password){
        super(username, password, RoleEnum.ADMIN);
    }
}
