package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iron_bank/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserById(
            @PathVariable (name = "id") String id
    ){
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/delete/customer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteActualCustomer(){
        return userService.deleteActualUser();
    }






    //Borrar usuarios -> solo el admin

    //Editar Usuarios -> solo el admin

    //


}
