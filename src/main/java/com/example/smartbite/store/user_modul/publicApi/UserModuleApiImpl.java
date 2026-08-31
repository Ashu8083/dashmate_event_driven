package com.example.smartbite.store.user_modul.publicApi;

import com.example.smartbite.store.common.execption.ResourceNotFoundException;
import com.example.smartbite.store.user_modul.internalModule.model.Users;
import com.example.smartbite.store.user_modul.internalModule.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserModuleApiImpl  implements UserModuleApi
 {
     final private UserRepo userRepo;
     public UserModuleApiImpl(UserRepo userRepo)
     {
        this.userRepo = userRepo;
     }

     @Override
     public UserAuthenticationData getUserAuthenticationData(UUID user_id) {

         return null;
     }

     @Override
     public UserModuleReplica getUserModuleReplica(UUID user_id) {
         log.info("getUserModuleReplica called for the user with User id "+ user_id);
         Users user = userRepo.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("User not found with this id :" + user_id));
         UserModuleReplica userModuleReplica = new UserModuleReplica(user.getId(),
                                                                     user.getName());
         return  userModuleReplica ;
     }

     @Override
     public UserModuleReplica createUserModel(String name, String number, String email) {
         log.info("createUserModel called for the user with name "+ name + " and number "+ number);
         Users user = new Users();
         user.setName(name);
         user.setNumber(number);
         user.setEmail(email);
         user = userRepo.save(user);

         UserModuleReplica userModuleReplica = new UserModuleReplica(
                 user.getId(),
                 user.getName()
         );
         return userModuleReplica ;
     }
 }
