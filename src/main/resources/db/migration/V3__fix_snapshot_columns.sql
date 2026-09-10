ALTER TABLE products RENAME COLUMN store_product_id to external_product_id;

ALTER TABLE order_items
    ALTER COLUMN external_product_id TYPE VARCHAR(255) USING external_product_id::VARCHAR;

ALTER TABLE orders ALTER COLUMN brand_id SET NOT NULL;
ALTER TABLE order_items ALTER COLUMN requested_quantity SET NOT NULL;