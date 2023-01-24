package com.ironhack.iron_bank_project.model;

import com.ironhack.iron_bank_project.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "password")
    })
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    //add an email patter
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    //@ManyToOne
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    public User(){}

    public User(String name, String email, String password, RoleEnum role){
        setName(name);
        setEmail(email);
        setPassword(password);
        setRole(role);
    }

}
