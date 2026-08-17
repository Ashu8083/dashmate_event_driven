CREATE TABLE  trip_assign(
                            id UUID PRIMARY KEY ,
                            rider_id UUID,
                            trip_stop_id UUID,
                            status order_status,
                            created_at TIMESTAMP,
                            updated_at TIMESTAMP,
                            cancelled_at TIMESTAMP NULL,
                            completed_at TIMESTAMP NULL,

                            FOREIGN KEY (rider_id)
                                REFERENCES riders(id)

);

ALTER TABLE trips
    DROP COLUMN rider_id,
    DROP COLUMN trip_stop_id,
    ADD COLUMN trip_assign_id UUID,
          ADD CONSTRAINT fk_trip_assign
            FOREIGN KEY (trip_assign_id)
            REFERENCES trip_assign(id);
