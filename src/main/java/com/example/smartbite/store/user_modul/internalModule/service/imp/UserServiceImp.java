package com.example.smartbite.store.user_modul.internalModule.service.imp;


import com.example.smartbite.store.user_modul.DTO.*;
import com.example.smartbite.store.user_modul.enums.UserStatusEnum;
import com.example.smartbite.store.user_modul.internalModule.model.UserAddress;
import com.example.smartbite.store.user_modul.internalModule.model.Users;
import com.example.smartbite.store.user_modul.internalModule.repo.UserAddressRepo;
import com.example.smartbite.store.user_modul.internalModule.repo.UserDeviceRepo;
import com.example.smartbite.store.user_modul.internalModule.repo.UserRefreshTokenRepo;
import com.example.smartbite.store.user_modul.internalModule.repo.UserRepo;
import com.example.smartbite.store.user_modul.internalModule.service.serviceinterface.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;

    private final UserAddressRepo userAddressRepo;

    public UserServiceImp(UserRepo userRepo, UserAddressRepo userAddressRepo,
                          UserDeviceRepo userDeviceRepo, UserRefreshTokenRepo userRefreshTokenRepo) {
        this.userRepo = userRepo;
        this.userAddressRepo = userAddressRepo;

    }


    @Override
    public UserResponseDTO getUserByEmail(String email) {
        Optional<Users> user = userRepo.findByEmail(email);
        if (user.isEmpty()) {
           throw  new RuntimeException("User not found");
        }
        Users foundUser = user.get();
        return new UserResponseDTO(
                foundUser.getEmail(),
                foundUser.getNumber(),
                foundUser.getName()
        );
    }

    @Override
    public UserCreateRequestDTO createUser(UserCreateRequestDTO data) {
        if (userRepo.existsByEmail(data.getEmail())) {
            throw new RuntimeException("User Already exist with this email");
        }
        Users users = new Users();
        users.setEmail(data.getEmail());
        users.setName(data.getName());
        users.setNumber(data.getNumber());
        Users userStore;
        try {
            userStore = (Users) userRepo.save(users);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating user", e);
        }
        return new UserCreateRequestDTO(
                userStore.getName(),
                userStore.getEmail(),
                userStore.getNumber()
        );
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserCreateRequestDTO data) {

        Users user  = userRepo.findByEmail(data.getEmail())
                .orElseThrow(()-> new RuntimeException("user not found"));

        if (data.getNumber() != null && !data.getNumber().isBlank()) {
            user.setNumber(data.getNumber());
        }

        return new UserResponseDTO(
                user.getEmail(),
                user.getNumber(),
                user.getName()
        );
    }



    @Override
    public UserResponseDTO findUserByName(String name, UserStatusEnum status) {
        Optional<Users> user = userRepo.findUserByName(name,status);

        if (user.isEmpty()){
            throw  new RuntimeException("User not found with"+ name);
        }
        Users foundUser = user.get();
        return new UserResponseDTO(
                foundUser.getName(),
                foundUser.getEmail(),
                foundUser.getNumber()
        );
    }


    @Override
    public UserAddressResponseDTO createUserAddress(UUID userId, UserAddressRequestDTO data) {
        Users user = userRepo.findById(userId).
                orElseThrow(()-> new RuntimeException("user not found"));
        UserAddress userAddress = new UserAddress();
        userAddress.setUsers(user);
        userAddress.setHouse_number(data.getHouse_no());
        userAddress.setCity(data.getCity());
        userAddress.setState(data.getState());
        userAddress.setStreet(data.getStreet());
        UserAddress storeAddress;
        try {
            userAddress = userAddressRepo.save(userAddress);
        } catch (Exception e) {
            throw new RuntimeException("User address creation Error");
        }
        return new UserAddressResponseDTO(
                userAddress.getHouse_number(),
                userAddress.getStreet(),
                userAddress.getCity(),
                userAddress.getState()
        );
    }

    @Override
    public UserStatusResponse checkUserStatus(String email) {
        Users user = userRepo.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found")) ;

        if (user.getStatus() == UserStatusEnum.ACTIVATE){
            return new UserStatusResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getStatus()
            );
        }
        throw new RuntimeException("User is Inactive");
    }

    @Transactional
    @Override
    public UserStatusResponse inactivateUser(String email) {
        Users user = userRepo.findByEmail(email)
                    .orElseThrow(()-> new RuntimeException("User not found"));

        if (user.getStatus() == UserStatusEnum.DEACTIVATE){
            throw  new RuntimeException("User Already Deactivate");
        }

        user.setStatus(UserStatusEnum.DEACTIVATE);

        return new UserStatusResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }

    @Transactional
    @Override
    public UserStatusResponse activateUser(String email) {
        Users user = userRepo.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        if (user.getStatus() == UserStatusEnum.ACTIVATE){
            throw  new RuntimeException("User Already activate");
        }

        user.setStatus(UserStatusEnum.ACTIVATE);

        return new UserStatusResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus()
        );
    }


}