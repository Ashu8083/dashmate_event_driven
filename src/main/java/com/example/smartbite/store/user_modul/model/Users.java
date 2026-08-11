package com.example.smartbite.store.user_modul.model;

import com.example.smartbite.store.user_modul.enums.UserStatusEnum;
import jakarta.persistence.*;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_name",nullable = false)
    private String name;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "phone_number", nullable = true ,unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private UserStatusEnum  status;

    @Column(name = "is_deleted")
    private Boolean is_deleted ;


}
