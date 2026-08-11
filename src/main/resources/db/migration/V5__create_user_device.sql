CREATE TYPE device_type_enum AS ENUM (
    'IOS',
    'ANDROID',
    'WEB'
);

CREATE TYPE status as ENUM (
    'LOGIN',
    'LOGOUT',
    'SUSPEND'
);
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID,
    refresh_token VARCHAR(255),
    expire_date DATE ,
    is_revoked BOOLEAN DEFAULT TRUE,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE user_device(
    id UUID PRIMARY KEY ,
    user_id UUID,
    refresh_token_id UUID,
    device_type   device_type_enum,
    last_login TIMESTAMPTZ,
    expire_date TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_refresh_token
        FOREIGN KEY (refresh_token_id)
            REFERENCES refresh_tokens(id)
            ON DELETE CASCADE
);