package com.ironhack.iron_bank_project.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class ConstraintViolationRestControllerAdvice {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ConstraintErrorMessage> handleException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::mapError)
                .collect(Collectors.toList());
    }

    private ConstraintErrorMessage mapError(ObjectError objectError) {
        if (objectError instanceof FieldError) {
            return new ConstraintErrorMessage(((FieldError) objectError).getField(),
                    objectError.getDefaultMessage());
        }
        return new ConstraintErrorMessage(objectError.getObjectName(), objectError.getDefaultMessage());
    }
}
