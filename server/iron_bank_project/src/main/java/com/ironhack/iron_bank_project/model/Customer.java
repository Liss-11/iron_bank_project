package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.dtoAuthentication.request.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.enums.StatusEnum;
import com.ironhack.iron_bank_project.utils.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
public class Customer extends User{

    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    public Customer(){}

    public Customer(String name, String email, String password, LocalDate dateOfBirth, Address primaryAddress, StatusEnum status){
        super(name, email, password, RoleEnum.ROLE_USER, status);
        setDateOfBirth(dateOfBirth);
        setPrimaryAddress(primaryAddress);
    }

    public static Customer fromRegisterCustomerRequest(RegisterCustomerRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new Customer(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()),
                LocalDate.parse(request.getDateOfBirth()),
                new Address(request.getStreet(), request.getCity(), request.getPostalCode(), request.getCountry()), StatusEnum.PENDENT);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }
}
