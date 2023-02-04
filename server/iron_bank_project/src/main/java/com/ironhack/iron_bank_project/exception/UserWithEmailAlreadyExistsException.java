package com.ironhack.iron_bank_project.exception;

public class UserWithEmailAlreadyExistsException extends RuntimeException {

    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }
    public UserWithEmailAlreadyExistsException() {
        super("User with this email Already exists");
    }
}
