CREATE TYPE user_role_enum AS ENUM (
    'CUSTOMER',
    'RIDER',
    'ADMIN'
);

ALTER TABLE users
    ADD COLUMN user_role user_role_enum DEFAULT 'CUSTOMER';