package com.example.smartbite.store.rider_modul.model;


import com.example.smartbite.store.rider_modul.enums.Gender;
import com.example.smartbite.store.user_modul.model.Users;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "riders" )
public class Riders{

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Gender gender;

}
