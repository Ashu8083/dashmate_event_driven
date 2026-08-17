package com.example.smartbite.store.user_modul.controller;


import com.example.smartbite.store.user_modul.DTO.*;
import com.example.smartbite.store.user_modul.service.serviceinterface.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create-user")
    UserCreateRequestDTO userCreateController(@Valid @RequestBody UserCreateRequestDTO data) {
        return userService.createUser(data);
    }

    @PostMapping("/create-userAddress")
    UserAddressResponseDTO createUserAddressController(@Valid @RequestBody UserAddressRequestDTO data) {
        return userService.createUserAddress(data);
    }

    @GetMapping("/get-user")
    UserResponseDTO getUserResponseController(@RequestParam @Email String email) {
        return userService.getUserByEmail(email);
    }


    @PutMapping("/upate-user")
    UserResponseDTO updateUserController(@Valid @RequestBody UserCreateRequestDTO data) {
        return userService.updateUser(data);

    }

    @GetMapping("/get-user-status{email}")
    UserStatusResponse getUserStatus(@RequestParam @Email @PathVariable String email) {
        return userService.checkUserStatus(email);
    }

    @PutMapping("/update-user-status-to-inactive")
    UserStatusResponse inactiveuser(@Valid @Email String email) {
        return userService.inactivateUser(email);
    }

    @PutMapping("/update-user-status-to-activate")
    UserStatusResponse activateUser(@Valid @Email String email) {
        return userService.activateUser(email);
    }

}