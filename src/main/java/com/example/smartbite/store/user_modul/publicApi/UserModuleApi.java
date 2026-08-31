package com.example.smartbite.store.user_modul.publicApi;

import java.util.UUID;
public interface UserModuleApi {

    UserAuthenticationData getUserAuthenticationData(UUID user_id);
    UserModuleReplica getUserModuleReplica(UUID user_id);
    UserModuleReplica  createUserModel(String name , String number ,String email);
}
