-------------------- POS Database --------------------

DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS restock;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS customer;

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
    CONSTRAINT CK_restock_status CHECK ( status IN ('PENDING', 'PROCESSED') ),
    FOREIGN KEY (product) REFERENCES product(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (supplier) REFERENCES supplier(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

-- Employee table

CREATE TABLE employee (
    id         VARCHAR(9) PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    phone      VARCHAR(20),
    CONSTRAINT UQ_employee_email UNIQUE (email)
);

-- Customer table

CREATE TABLE customer (
    id         VARCHAR(9) PRIMARY KEY,
    first_name VARCHAR (50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    phone      VARCHAR(20),
    address    VARCHAR(50),
    birthday   DATE
);

-- Order table

CREATE TABLE cart (
    id             UUID PRIMARY KEY,
    date           DATE,
    net            DECIMAL(10, 2) DEFAULT 0.00,
    tax            DECIMAL(10, 2) DEFAULT 0.00,
    total          DECIMAL(10, 2) DEFAULT 0.00,
    payment_method VARCHAR(15) NOT NULL DEFAULT 'CASH',
    status         VARCHAR(15) NOT NULL DEFAULT 'IN_PROGRESS',
    customer       VARCHAR(9),
    employee       VARCHAR(9) NOT NULL,
    FOREIGN KEY (customer) REFERENCES customer(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (employee) REFERENCES employee(id) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT CK_order_payment_method CHECK ( payment_method IN ('CASH', 'DEBT_CARD', 'CREDIT_CARD') ),
    CONSTRAINT CK_order_status CHECK ( status IN ('IN_PROGRESS', 'PROCESSED') )
);

-- Item table

CREATE TABLE cart_item (
    code    UUID PRIMARY KEY,
    product VARCHAR(9) NOT NULL,
    cart    UUID NOT NULL,
    FOREIGN KEY (product) REFERENCES product(id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (cart) REFERENCES cart(id) ON DELETE CASCADE ON UPDATE CASCADE
);






