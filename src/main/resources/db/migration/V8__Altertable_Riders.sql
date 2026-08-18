ALTER TABLE riders
    ADD COLUMN is_available BOOLEAN NOT NULL DEFAULT FALSE,
    ADD COLUMN longitude DOUBLE PRECISION,
    ADD COLUMN latitude DOUBLE PRECISION;

ALTER TABLE trips
    ADD COLUMN pickup_id UUID,
    ADD COLUMN drop_off_id UUID,

    ADD CONSTRAINT fk_trip_pickup
        FOREIGN KEY (pickup_id)
        REFERENCES trip_stops(id),

    ADD CONSTRAINT fk_trip_drop_off
        FOREIGN KEY (drop_off_id)
        REFERENCES trip_stops(id);

ALTER TABLE trip_assign
    ADD COLUMN  trip_id UUID,
    DROP  COLUMN  trip_stop_id ,
    ADD CONSTRAINT fk_trip_id
        FOREIGN KEY (trip_id)
        REFERENCES trips(id);