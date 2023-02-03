package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dtos.request.CreateCheckingAccountRequest;
import com.ironhack.iron_bank_project.accounts.dtos.request.UpdateCheckingAccountRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.users.model.User;
import com.ironhack.iron_bank_project.utils.Money;
import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class StudentCheckingAccount extends Account {

    private String secretKey;

    @Override
    public void setBalance(Money balance){
        if(balance.getAmount().compareTo(BigDecimal.valueOf(0.0)) <= 0){
            super.setStatus(AccountStatus.EMPTY);
            if(super.getCreditCard() != null){
                super.getCreditCard().setStatus(AccountStatus.FROZEN);
            }
        }
        else{
            if(super.getStatus() == AccountStatus.EMPTY){super.setStatus(AccountStatus.ACTIVE);}
            if(super.getCreditCard() != null) {
                if (super.getCreditCard().getStatus() == AccountStatus.FROZEN) {
                    super.getCreditCard().setStatus(AccountStatus.ACTIVE);
                }
            }
        }
        super.setBalance(balance);
    }


    public StudentCheckingAccount() {}

    public StudentCheckingAccount(BigDecimal balance, User primaryOwner, User secondaryOwner, String secretKey) {
        super(balance, AccountType.STUDENT, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }

    public static StudentCheckingAccount fromDTO(CreateCheckingAccountRequest dto, User owner, User secondaryOwner){
        return new StudentCheckingAccount(
                dto.getInitialAmount(),
                owner,
                secondaryOwner,
                dto.getSecretKey()
        );
    }

    public static StudentCheckingAccount updateFromDTO(UpdateCheckingAccountRequest dto, StudentCheckingAccount reference, Customer user){
        if(dto.getBalance() != null){reference.setBalance(new Money(dto.getBalance()));}
        if(user != null){reference.setSecondaryOwner(user);}
        if(dto.getSecretKey() != null){reference.setSecretKey(dto.getSecretKey());}
        if(dto.getStatus() != null){
            try{
                reference.setStatus(dto.getAccountStatus());
            }catch(Exception e){
                throw new IllegalArgumentException("Invalid account Status");
            }
        }
        return reference;
    }

}
