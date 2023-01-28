package com.ironhack.iron_bank_project.accounts;

import com.ironhack.iron_bank_project.users.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "owner_id")
    private User primaryOwner;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "secundary_owner_id")
    private User secondaryOwner;

    private final BigDecimal PENALITY_FEE = BigDecimal.valueOf(40.0);

    public Account(BigDecimal balance, User primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
    }

    public Account() {

    }
}
