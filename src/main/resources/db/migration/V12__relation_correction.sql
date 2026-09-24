ALTER TABLE trips
DROP CONSTRAINT IF EXISTS trips_pickup_id_fkey,
DROP CONSTRAINT IF EXISTS trips_drop_off_id_fkey,
DROP CONSTRAINT IF EXISTS trips_trip_assign_id_fkey;

ALTER TABLE trips
DROP COLUMN IF EXISTS pickup_id,
DROP COLUMN IF EXISTS drop_off_id,
DROP COLUMN IF EXISTS trip_assign_id;