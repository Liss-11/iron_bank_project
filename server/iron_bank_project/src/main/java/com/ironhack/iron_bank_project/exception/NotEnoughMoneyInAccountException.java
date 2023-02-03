package com.ironhack.iron_bank_project.exception;

public class NotEnoughMoneyInAccountException extends RuntimeException{
    public NotEnoughMoneyInAccountException(String message) {
        super(message);
    }

    public NotEnoughMoneyInAccountException() {
        super("User not found!");
    }
}
