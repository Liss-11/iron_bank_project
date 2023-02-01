package com.ironhack.iron_bank_project.controller;

import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.ChangeStatusRequest;
import com.ironhack.iron_bank_project.users.dtos.dtoAuthentication.request.UpdateCustomerRequest;
import com.ironhack.iron_bank_project.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable (name = "id") String id,
                                        @Valid @RequestBody UpdateCustomerRequest request){
        return userService.updateUser(id, request);
    }

    @PatchMapping("/change_status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeStatus(@PathVariable (name = "id") String id,
                                        @Valid @RequestBody ChangeStatusRequest request){
        return userService.changeStatus(id, request);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUserById(
            @PathVariable (name = "id") String id
    ){
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/delete/current_customer")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteActualCustomer(){
        return userService.deleteActualUser();
    }

}
