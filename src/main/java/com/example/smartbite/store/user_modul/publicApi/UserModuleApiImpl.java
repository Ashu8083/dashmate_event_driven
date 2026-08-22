package com.example.smartbite.store.user_modul.publicApi;

import com.example.smartbite.store.user_modul.internalModule.repo.UserRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
         return null;
     }
 }
