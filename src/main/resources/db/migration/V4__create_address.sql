CREATE TABLE user_address(
    id UUID PRIMARY KEY ,
    user_id UUID ,
    house_number VARCHAR(25),
    street VARCHAR(100),
    city VARCHAR(80),
    state VARCHAR(40),

    FOREIGN KEY (user_id)
        REFERENCES users(id)
);