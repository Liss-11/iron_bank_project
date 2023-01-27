package com.ironhack.iron_bank_project.validation;

import com.ironhack.iron_bank_project.exception.CustomerWithEmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    //Error response in case the "findById" POST (prom Post model)doesn't exist
    @ExceptionHandler(CustomerWithEmailAlreadyExistsException.class)
    public ResponseEntity<Object> CustomerWithEmailAlreadyExistsException
    (
            CustomerWithEmailAlreadyExistsException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now().toString());
        body.put("message", "A user with this email already exists");

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}

