-------------------- POS Database --------------------

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;

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
    acquisition_cost NUMERIC(10, 2) NOT NULL ,
    selling_price    NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (category) REFERENCES category(id) ON DELETE RESTRICT ON UPDATE CASCADE
);