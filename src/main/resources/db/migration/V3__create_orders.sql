CREATE TYPE user_status AS ENUM (
    'ACTIVATE',
    'SUSPENDED',
    'DEACTIVATE'
);

ALTER TABLE users
    ADD COLUMN phone_number VARCHAR(13),
ADD COLUMN status user_status,
ADD COLUMN is_deleted BOOLEAN DEFAULT TRUE;