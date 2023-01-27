package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import com.ironhack.iron_bank_project.enums.StatusEnum;
import jakarta.persistence.Entity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class ThirdParty extends User {

    private String hashedKey;

    public ThirdParty(String name, String email, String password, String key, StatusEnum status){
        super(name, email, password, RoleEnum.ROLE_THIRDPARTY, status);
        setHashKey(key);

    }

    public ThirdParty() {

    }

    public String getHashKey() {
        return hashedKey;
    }

    public void setHashKey(String hashKey) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.hashedKey = passwordEncoder.encode(hashKey);
    }
}
