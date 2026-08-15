create table restaurants (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) not null,
    address VARCHAR(255),
    email VARCHAR(100) not null UNIQUE,
    password_hash VARCHAR(60) not null,
    api_key VARCHAR(64) UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
)