package com.ironhack.iron_bank_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iron_bank/demo")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello Alissia");
    }
}
