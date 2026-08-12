CREATE TYPE order_status AS ENUM (
    'CANCELED',
    'PENDING',
    'ASSIGNED',
    'DELIVERED',
    'OUT_FOR_DELIVERY'

);

CREATE TYPE gender as ENUM(
       'MALE',
       'FEMALE'
       'OTHER'
)
CREATE TABLE riders(
    id UUID PRIMARY KEY,
    user_id UUID,
    age INTEGER,
    gender gender
)
CREATE TABLE payment
(
    id UUID PRIMARY KEY ,
    payment_method VARCHAR(25),
    total_charge DECIMAL(10,2),
    is_payment_successful BOOLEAN DEFAULT FALSE

)

CREATE TABLE trips (
    id UUID PRIMARY KEY ,
    customer_id UUID ,
    rider_id UUID,
    payment_id UUID,
    trip_stop_id UUID,
    status order_status,
    package_description String(225),

    scheduled_at TIMESTAMP,
    discount_amount DECIMAL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    cancelled_at TIMESTAMP NULL,
    completed_at TIMESTAMP NULL,

    FOREIGN KEY (customer_id)
            REFERENCES users(id)

    FOREIGN KEY (payment_id)
            REFERENCES payment(id)

    FOREIGN KEY (rider_id)
        REFERENCES riders(id)
)

CREATE TYPE stop_type AS ENUM (
    'PICKUP',
    'INTERMEDIATE',
    'DROP'
);

CREATE TABLE trip_stops (
                            id UUID PRIMARY KEY,

                            trip_id UUID NOT NULL,

                            stop_type stop_type NOT NULL,
                            sequence_number INTEGER NOT NULL,

                            address VARCHAR(255) NOT NULL,

                            latitude DECIMAL(10,8),
                            longitude DECIMAL(11,8),

                            contact_name VARCHAR(100),
                            contact_phone VARCHAR(20),

                            created_at TIMESTAMP NOT NULL,

                            FOREIGN KEY (trip_id)
                                REFERENCES trips(id)
);



