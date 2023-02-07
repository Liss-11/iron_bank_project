package com.ironhack.iron_bank_project.accounts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ironhack.iron_bank_project.accounts.service.AccountService;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.transactions.Transaction;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.utils.Money;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @Embedded
    private Money balance;

   // private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;


    @Enumerated(EnumType.STRING)
    private AccountStatus status;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinTable(name = "owner_id")
    private User primaryOwner;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinTable(name = "secundary_owner_id")
    private User secondaryOwner;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> fromAccountTransaction;

    @OneToMany(mappedBy = "to", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> toAccountTransaction;

    // @JoinColumn(name = "principal_account")
    @OneToOne(mappedBy = "associatedToAccount", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private CreditCardAccount creditCard;

    public Account(BigDecimal balance, AccountType accountType, AccountStatus status, User primaryOwner, User secondaryOwner) {
        this.balance = new Money(balance);
        this.accountType = accountType;
        this.status = status;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public Account() {}




}
