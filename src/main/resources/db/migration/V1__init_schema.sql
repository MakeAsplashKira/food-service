-- Baseline schema for all current entities.

CREATE TABLE brands
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    image_url     VARCHAR(255),
    CONSTRAINT uk_brands_name UNIQUE (name),
    CONSTRAINT uk_brands_email UNIQUE (email)
);

CREATE TABLE products
(
    id                BIGSERIAL PRIMARY KEY,
    brand_id          BIGINT       NOT NULL,
    store_product_id  VARCHAR(255) NOT NULL,
    name              VARCHAR(255) NOT NULL,
    image_url         VARCHAR(255),
    category          VARCHAR(255),
    CONSTRAINT fk_products_brand FOREIGN KEY (brand_id) REFERENCES brands (id)
);

CREATE INDEX idx_products_brand_id ON products (brand_id);

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    number        VARCHAR(15) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    name          VARCHAR(255),
    address       VARCHAR(255),
    created_at    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT uk_users_number UNIQUE (number)
);

CREATE TABLE stores
(
    id            BIGSERIAL PRIMARY KEY,
    brand_id      BIGINT       NOT NULL,
    address       VARCHAR(255) NOT NULL,
    email         VARCHAR(100) NOT NULL,
    password_hash VARCHAR(60)  NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE,
    CONSTRAINT uk_stores_email UNIQUE (email)
);

CREATE INDEX idx_stores_brand_id ON stores (brand_id);

CREATE TABLE stocks
(
    id                 BIGSERIAL PRIMARY KEY,
    store_id           BIGINT         NOT NULL,
    product_id         BIGINT         NOT NULL,
    available_quantity INTEGER        NOT NULL,
    unit_price         NUMERIC(19, 2) NOT NULL,
    CONSTRAINT fk_stocks_store FOREIGN KEY (store_id) REFERENCES stores (id),
    CONSTRAINT uk_stock UNIQUE (store_id, product_id)
);

CREATE INDEX idx_stocks_product_id ON stocks (product_id);

CREATE TABLE orders
(
    id           BIGSERIAL PRIMARY KEY,
    version      BIGINT,
    store_id     BIGINT      NOT NULL,
    user_id      BIGINT      NOT NULL,
    status       VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE,
    pending_at   TIMESTAMP WITH TIME ZONE,
    delivered_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_orders_user_id ON orders (user_id);
CREATE INDEX idx_orders_store_id ON orders (store_id);

CREATE TABLE order_items
(
    id                    BIGSERIAL PRIMARY KEY,
    version               BIGINT,
    order_id              BIGINT         NOT NULL,
    menu_item_id          BIGINT         NOT NULL,
    provider_menu_item_id BIGINT         NOT NULL,
    name                  VARCHAR(255)   NOT NULL,
    unit_price            NUMERIC(19, 2) NOT NULL,
    category              VARCHAR(255),
    quantity              INTEGER        NOT NULL,
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

CREATE INDEX idx_order_items_order_id ON order_items (order_id);
