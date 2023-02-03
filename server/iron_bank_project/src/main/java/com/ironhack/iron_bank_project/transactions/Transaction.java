package com.ironhack.iron_bank_project.transactions;

import com.ironhack.iron_bank_project.accounts.model.Account;
import com.ironhack.iron_bank_project.enums.TransactionType;
import com.ironhack.iron_bank_project.transactions.dtos.request.TransferRequest;
import com.ironhack.iron_bank_project.utils.Money;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime creationInstant;

    private String fromThirdParty;

    @ManyToOne()
    @JoinColumn(name = "fromAccount_id")
    private Account from;

    @ManyToOne()
    @JoinColumn(name = "toAccount_id")
    private Account to;

    private Money amount;

    private TransactionType type;

    @Column(length = 500)
    private String description;

    public Transaction() {
    }

    public Transaction(Account from, Account to, Money amount, TransactionType type, String description) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.type = type;
        setDescription(description);
    }

    public Transaction(LocalDateTime creationInstant, String fromThirdParty, Account to, Money amount, TransactionType type, String description) {
        this.creationInstant = creationInstant;
        this.fromThirdParty = fromThirdParty;
        this.to = to;
        this.amount = amount;
        this.type = type;
        setDescription(description);
    }
}
