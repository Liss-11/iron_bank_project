package com.ironhack.iron_bank_project.transactions;

import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.enums.TransactionType;
import com.ironhack.iron_bank_project.utils.Money;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "fromAccount_id")
    private Account from;

    @ManyToOne()
    @JoinColumn(name = "toAccount_id")
    private Account to;

    private Money amount;

    private TransactionType type;
}
