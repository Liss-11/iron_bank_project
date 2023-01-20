package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.utils.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class AccountHolder extends User{

    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    private String mailingAddress;

    public AccountHolder(){}

    public AccountHolder(String name, String password, LocalDate dateOfBirth, Address primaryAddress){
        super(name, password, RoleEnum.USER);
        setDateOfBirth(dateOfBirth);
        setPrimaryAddress(primaryAddress);
    }

    public AccountHolder(String name, String password, LocalDate dateOfBirth, Address primaryAddress, String mailingAddress ){
        super(name, password, RoleEnum.USER);
        setDateOfBirth(dateOfBirth);
        setPrimaryAddress(primaryAddress);
        setMailingAddress(mailingAddress);
    }


}
