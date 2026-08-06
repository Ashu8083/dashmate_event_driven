package com.example.smartbite.store.user_modul.service.serviceinterface;

import org.apache.catalina.User;
import org.hibernate.internal.util.Optional;

import java.util.UUID;

public interface UserService {

    Optional<User> findUserById (UUID id);

    Boolean chcekUserExist (UUID id) ;

    Optional<User> findUserByNumber (String number);

}
