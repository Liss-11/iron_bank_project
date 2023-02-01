package com.ironhack.iron_bank_project.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;


    @Enumerated(EnumType.STRING)
    private AccountStatus status;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "owner_id")
    private User primaryOwner;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinTable(name = "secundary_owner_id")
    private User secondaryOwner;

    public Account(BigDecimal balance, AccountType accountType, AccountStatus status, User primaryOwner, User secondaryOwner) {
        this.balance = balance;
        this.accountType = accountType;
        this.status = status;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public Account() {

    }
}
