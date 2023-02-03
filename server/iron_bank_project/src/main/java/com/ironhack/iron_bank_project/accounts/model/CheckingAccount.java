package com.ironhack.iron_bank_project.accounts.model;

import com.ironhack.iron_bank_project.accounts.dtos.request.CreateCheckingAccountRequest;
import com.ironhack.iron_bank_project.accounts.dtos.request.UpdateCheckingAccountRequest;
import com.ironhack.iron_bank_project.enums.AccountStatus;
import com.ironhack.iron_bank_project.enums.AccountType;
import com.ironhack.iron_bank_project.users.model.Customer;
import com.ironhack.iron_bank_project.utils.Money;
import jakarta.persistence.Entity;
import jdk.jfr.Timestamp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class CheckingAccount extends Account implements FeesInterface {

    @Timestamp
    private LocalDateTime maintenanceFeeDate = LocalDateTime.now().plusMonths(1L);

    private String secretKey;

    private BigDecimal minimumBalance = BigDecimal.valueOf(250.0);

    private BigDecimal monthlyMaintenanceFee = BigDecimal.valueOf(12.0);

    private BigDecimal penalityFee = BigDecimal.valueOf(40.0);

    private boolean penalized = false;

    public CheckingAccount() {
    }


    //TODO -> Bloquear las CreditCard, si tiene alguna asociada!! (desde una máscara)


    @Override
    public void setBalance(Money balance) {
        if (balance.getAmount().compareTo(BigDecimal.valueOf(0.0)) <= 0) {
            super.setStatus(AccountStatus.EMPTY);
            if(super.getCreditCard() != null){
                super.getCreditCard().setStatus(AccountStatus.FROZEN);
            }
        } else if (balance.getAmount().compareTo(getMinimumBalance()) < 0) {
            if (!penalized) {
                balance = new Money(balance.decreaseAmount(penalityFee));
                penalized = true;
            }
            if (super.getStatus() == AccountStatus.EMPTY) {
                super.setStatus(AccountStatus.ACTIVE);
            }
        } else {
            if (penalized) {
                penalized = false;
            }
            if (super.getStatus() == AccountStatus.EMPTY) {
                super.setStatus(AccountStatus.ACTIVE);
            }
            if(super.getCreditCard() != null) {
                if (super.getCreditCard().getStatus() == AccountStatus.FROZEN) {
                    super.getCreditCard().setStatus(AccountStatus.ACTIVE);
                }
            }
            super.setBalance(balance);
        }
    }

    public CheckingAccount(BigDecimal balance, Customer primaryOwner, Customer secondaryOwner, String secretKey) {
        super(balance, AccountType.CHECKING, AccountStatus.ACTIVE, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }

    public static CheckingAccount fromDTO (
            CreateCheckingAccountRequest request, Customer owner, Customer secondaryOwner){
        CheckingAccount account = new CheckingAccount();
        if(request.getInitialAmount().compareTo(account.getMinimumBalance()) < 0 ){
            throw new IllegalArgumentException("The initial amount must be greater than the MinimumBalance: 250€");
        }
        account.setBalance(new Money(request.getInitialAmount()));
        account.setPrimaryOwner(owner);
        if(secondaryOwner != null){account.setSecondaryOwner(secondaryOwner);}
        account.setSecretKey(request.getSecretKey());
        return account;
    }

    public static CheckingAccount updateFromDTO(UpdateCheckingAccountRequest dto, CheckingAccount reference, Customer user){
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
        if(dto.getMinimumBalance() != null){reference.setMinimumBalance(dto.getMinimumBalance());}
        return reference;
    }

}
