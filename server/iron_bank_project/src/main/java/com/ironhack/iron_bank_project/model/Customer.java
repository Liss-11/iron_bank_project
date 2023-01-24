package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.utils.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Customer extends User{

    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    public Customer(){}

    public Customer(String name, String email, String password, LocalDate dateOfBirth, Address primaryAddress){
        super(name, email, password, RoleEnum.USER);
        setDateOfBirth(dateOfBirth);
        setPrimaryAddress(primaryAddress);
    }


}
