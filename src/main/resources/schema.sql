CREATE TABLE IF NOT EXISTS category (
    id   VARCHAR(9) PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
    id               VARCHAR(9) PRIMARY KEY,
    name             VARCHAR(50) NOT NULL,
    category         VARCHAR(9) NOT NULL,
    stock            INTEGER DEFAULT 0,
    min_stock        INTEGER DEFAULT 0,
    acquisition_cost NUMERIC(10, 2) NOT NULL ,
    selling_price    NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (category) REFERENCES category(id) ON DELETE CASCADE ON UPDATE CASCADE
);