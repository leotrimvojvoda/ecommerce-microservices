CREATE TABLE t_orders
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    order_number VARCHAR(255) NULL,
    sku_code     VARCHAR(255) NULL,
    price        DECIMAL NULL,
    quantity     INT NULL,
    CONSTRAINT pk_t_orders PRIMARY KEY (id)
);