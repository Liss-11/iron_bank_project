package com.ironhack.iron_bank_project.exception;

public class AccountCanNotBeCreated extends RuntimeException{

    public AccountCanNotBeCreated(String message) {
        super(message);
    }

    public AccountCanNotBeCreated() {
        super("Account should not be created! Contact with your bank");
    }
}
