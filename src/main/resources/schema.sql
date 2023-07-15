-------------------- POS Database --------------------

DROP TABLE IF EXISTS restock;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS supplier;

-- Category table

CREATE TABLE category (
    id   VARCHAR(9) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Product table

CREATE TABLE product (
    id               VARCHAR(9) PRIMARY KEY,
    name             VARCHAR(50) NOT NULL,
    category         VARCHAR(9) NOT NULL,
    stock            INTEGER DEFAULT 0,
    min_stock        INTEGER DEFAULT 0,
    acquisition_cost NUMERIC(10, 2) NOT NULL,
    selling_price    NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (category) REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Supplier table

CREATE TABLE supplier (
    id      VARCHAR(9) PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    contact VARCHAR(20) NOT NULL
);

-- Restock table

CREATE TABLE restock (
    id               UUID PRIMARY KEY,
    delivery_date    DATE,
    product          VARCHAR(9) NOT NULL,
    product_quantity INTEGER NOT NULL,
    supplier         VARCHAR(9) NOT NULL,
    status           VARCHAR(15) NOT NULL,
    CONSTRAINT chk_restock_status CHECK ( status IN ('PENDING', 'PROCESSED', 'CANCELLED') ),
    FOREIGN KEY (product) REFERENCES product(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (supplier) REFERENCES supplier(id) ON DELETE RESTRICT ON UPDATE CASCADE
);






