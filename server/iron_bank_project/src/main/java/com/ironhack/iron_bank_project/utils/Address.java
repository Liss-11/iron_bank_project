package com.ironhack.iron_bank_project.utils;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;

    public Address(String street, String city, String postalCode, String country){
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setCountry(country);
    }

    public Address() {

    }
}
