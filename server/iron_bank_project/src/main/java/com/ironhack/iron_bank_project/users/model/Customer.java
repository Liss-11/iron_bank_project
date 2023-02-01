package com.ironhack.iron_bank_project.users.model;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.RegisterCustomerRequest;
import com.ironhack.iron_bank_project.enums.Role;
import com.ironhack.iron_bank_project.enums.UserStatus;
import com.ironhack.iron_bank_project.utils.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DateTimeException;
import java.time.LocalDate;

@Entity
public class Customer extends User {

    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    public Customer(){}

    public Customer(String name, String email, String password, LocalDate dateOfBirth, Address primaryAddress, UserStatus status){
        super(name, email, password, Role.ROLE_USER, status);
        setDateOfBirth(dateOfBirth);
        setPrimaryAddress(primaryAddress);
    }

    public static Customer fromRegisterCustomerRequest(RegisterCustomerRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new Customer(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()),
                LocalDate.parse(request.getDateOfBirth()),
                new Address(request.getStreet(), request.getCity(), request.getPostalCode(), request.getCountry()), UserStatus.PENDENT);
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth (LocalDate dateOfBirth) throws IllegalArgumentException {
        try{
            if (dateOfBirth.plusYears(18L).isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("You must be older than 18 years old!");
            }
            if (dateOfBirth.plusYears(120L).isBefore(LocalDate.now())){
                throw new IllegalArgumentException("Maximum accepted age is 120 years old!");
            }
            this.dateOfBirth = dateOfBirth;
        }catch (Exception e){
            throw new DateTimeException("Invalid date!");
        }

    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }
}
