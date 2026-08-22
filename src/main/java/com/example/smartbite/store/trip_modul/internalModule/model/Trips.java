package com.example.smartbite.store.trip_modul.internalModule.model;
import com.example.smartbite.store.trip_modul.internalModule.enums.OrderStatus;
import com.example.smartbite.store.trip_modul.internalModule.model.TripStops;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trips")
public class Trips {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id")
    private UUID customerId;

    @OneToOne(mappedBy = "trip",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private  TripAssign tripAssign;

    @OneToMany(mappedBy = "trip",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<TripStops> tripStop;

    @Column(name = "pickup_id")
    private UUID pickupId;

    @Column(name = "drop_off_id")
    private UUID dropOffId;

    @Column(name = "payment_id")
    private UUID paymentId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private OrderStatus status;

    @Column(name= "package_description")
    private String package_description;

    @Column(name= "scheduled_at")
    private Instant scheduled_at;

    @Column(name= "created_at")
    private Instant created_at;


    @Column(name= "updated_at")
    private Instant updated_at;
    @Column(name= "cancelled_at")
    private Instant cancelled_at;

    @Column(name= "completed_at")
    private Instant completed_at;


}
