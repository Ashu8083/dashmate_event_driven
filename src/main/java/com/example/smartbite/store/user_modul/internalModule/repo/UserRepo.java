package com.example.smartbite.store.user_modul.internalModule.repo;

import com.example.smartbite.store.user_modul.enums.UserStatusEnum;
import com.example.smartbite.store.user_modul.internalModule.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
    public interface UserRepo extends JpaRepository <Users,UUID>  {

        Optional<Users> findByEmail(String email);

        boolean existsByEmail(String email);

        List<Users> findByName(String name);

        List<Users> findByNameContainingIgnoreCase(String name);


        @Query("""
            SELECT u
            FROM Users u
            WHERE u.status = :status
            AND u.name = :name
        """)
        Optional<Users> findUserByName(
                @Param("name") String name,
                @Param("status") UserStatusEnum status
        );
    }