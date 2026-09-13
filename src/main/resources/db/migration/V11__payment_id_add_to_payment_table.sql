ALTER TABLE payment
    ADD COLUMN payment_unique_id VARCHAR(100) UNIQUE,
    ADD COLUMN status order_status NOT NULL DEFAULT 'PENDING';