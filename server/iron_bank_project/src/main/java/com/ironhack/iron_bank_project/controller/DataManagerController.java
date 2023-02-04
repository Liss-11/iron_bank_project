package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/automatic_data_manager")
public class DataManagerController {

    private final Validator validator;

    @GetMapping()
    public ResponseEntity<?> automaticDataManager(){
        return validator.updateAccountsInfo();
    }
}
