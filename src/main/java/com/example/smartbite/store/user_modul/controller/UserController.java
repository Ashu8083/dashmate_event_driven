package com.example.smartbite.store.user_modul.controller;


import com.example.smartbite.store.user_modul.DTO.UserAddressRequestDTO;
import com.example.smartbite.store.user_modul.DTO.UserAddressResponseDTO;
import com.example.smartbite.store.user_modul.DTO.UserCreateRequestDTO;
import com.example.smartbite.store.user_modul.DTO.UserResponseDTO;
import com.example.smartbite.store.user_modul.service.serviceinterface.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/create-user")
    UserCreateRequestDTO userCreateController(@Valid @RequestBody UserCreateRequestDTO data){
            return userService.createUser(data);
        }

    @PostMapping("/create-userAddress")
    UserAddressResponseDTO createUserAddressController(@Valid @RequestBody UserAddressRequestDTO data){
        return userService.createUserAddress(data);
    }

    @GetMapping("/get-user")
    UserResponseDTO getUserResponseController(@Valid @Email String email){
        return  userService.getUserByEmail(email);
    }


    @PutMapping("/upate-user")
    UserResponseDTO updateUserController(@Valid @RequestBody UserCreateRequestDTO data){
        return userService.updateUser(data);

    }

}
