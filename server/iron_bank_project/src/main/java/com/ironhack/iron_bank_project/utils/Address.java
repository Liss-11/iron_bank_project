package com.ironhack.iron_bank_project.utils;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
public class Address {
    private String street;
    private String city;
    private String postalCode;
    private String country;
}
