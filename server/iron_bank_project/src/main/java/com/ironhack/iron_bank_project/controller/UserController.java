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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //TODO -> GET Users (admin)
    //TODO -> GET UserById (admin)

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable (name = "id") String id,
                                        @Valid @RequestBody UpdateCustomerRequest request){
        return userService.updateUser(id, request);
    }

    @PatchMapping("/admin/change_status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable (name = "id") String id,
                                        @Valid @RequestBody ChangeStatusRequest request){
        return userService.changeStatus(id, request);
    }


    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable (name = "id") String id
    ){
        return userService.deleteUserById(id);
    }

    @DeleteMapping("customer/delete/current_customer")
    public ResponseEntity<?> deleteActualCustomer(){
        return userService.deleteActualUser();
    }

}
