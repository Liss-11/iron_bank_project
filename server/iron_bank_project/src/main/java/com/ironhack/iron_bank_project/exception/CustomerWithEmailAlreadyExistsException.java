package com.ironhack.iron_bank_project.exception;

public class CustomerWithEmailAlreadyExistsException extends RuntimeException {
    private String message;

    public CustomerWithEmailAlreadyExistsException() {
        super("User with this email already exists");
    }

    public CustomerWithEmailAlreadyExistsException(String msg)
        {
            super(msg);
            this.message = msg;
        }
}
