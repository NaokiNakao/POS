-- Category insertions
INSERT INTO category (id, name) VALUES ('CAT000001', 'Building');
INSERT INTO category (id, name) VALUES ('CAT000002', 'Plumbing');
INSERT INTO category (id, name) VALUES ('CAT000003', 'Paint');
INSERT INTO category (id, name) VALUES ('CAT000004', 'Gardening');
INSERT INTO category (id, name) VALUES ('CAT000005', 'Power Tools');

-- Product insertions
INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000001', 'Drill', 'CAT000005', 200, 20, 43.99, 51.83);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000002', 'Hammer', 'CAT000001', 150, 10, 12.99, 18.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000003', 'Screwdriver Set', 'CAT000005', 100, 15, 19.99, 24.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000004', 'Paint Roller', 'CAT000003', 75, 5, 8.99, 12.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000005', 'Garden Shears', 'CAT000004', 50, 10, 14.99, 19.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000006', 'Pipe Wrench', 'CAT000002', 80, 15, 24.99, 32.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000007', 'Cordless Drill', 'CAT000005', 120, 30, 79.99, 99.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000008', 'Paint Brushes', 'CAT000003', 200, 20, 6.99, 9.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000009', 'Lawn Mower', 'CAT000004', 40, 5, 149.99, 179.99);

INSERT INTO product (id, name, category, stock, min_stock, acquisition_cost, selling_price)
VALUES ('PRO000010', 'Plunger', 'CAT000002', 60, 10, 4.99, 7.99);

-- Supplier insertions
INSERT INTO supplier (id, name, address, contact)
VALUES ('SUP000001', 'Supplier 1', 'Address 1', 'Contact 1');

INSERT INTO supplier (id, name, address, contact)
VALUES ('SUP000002', 'Supplier 2', 'Address 2', 'Contact 2');

INSERT INTO supplier (id, name, address, contact)
VALUES ('SUP000003', 'Supplier 3', 'Address 3', 'Contact 3');

INSERT INTO supplier (id, name, address, contact)
VALUES ('SUP000004', 'Supplier 4', 'Address 4', 'Contact 4');

INSERT INTO supplier (id, name, address, contact)
VALUES ('SUP000005', 'Supplier 5', 'Address 5', 'Contact 5');

-- Restock insertions
INSERT INTO restock (id, delivery_date, product, product_quantity, supplier, status)
VALUES (gen_random_uuid(), '2023-07-01', 'PRO000001', 100, 'SUP000001', 'PENDING');

INSERT INTO restock (id, delivery_date, product, product_quantity, supplier, status)
VALUES (gen_random_uuid(), '2023-07-02', 'PRO000002', 50, 'SUP000002', 'PROCESSED');

INSERT INTO restock (id, delivery_date, product, product_quantity, supplier, status)
VALUES (gen_random_uuid(), '2023-07-03', 'PRO000003', 80, 'SUP000003', 'PENDING');

INSERT INTO restock (id, delivery_date, product, product_quantity, supplier, status)
VALUES (gen_random_uuid(), '2023-07-04', 'PRO000004', 60, 'SUP000004', 'CANCELLED');

INSERT INTO restock (id, delivery_date, product, product_quantity, supplier, status)
VALUES (gen_random_uuid(), '2023-07-05', 'PRO000005', 90, 'SUP000005', 'PENDING');

-- Employee insertions
INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000001', 'John', 'Doe', 'john.doe@example.com', 'password123', '1234567890');

INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000002', 'Jane', 'Smith', 'jane.smith@example.com', 'password456', '9876543210');

INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000003', 'Michael', 'Johnson', 'michael.johnson@example.com', 'password789', '4567890123');

INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000004', 'Sarah', 'Williams', 'sarah.williams@example.com', 'passwordabc', '8901234567');

INSERT INTO employee (id, first_name, last_name, email, password, phone)
VALUES ('EMP000005', 'David', 'Brown', 'david.brown@example.com', 'passwordxyz', '2345678901');

-- Customer insertions
INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000001', 'Emily', 'Johnson', '1234567890', '123 Main St', '1990-05-15');

INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000002', 'Daniel', 'Smith', '9876543210', '456 Oak Ave', '1985-10-12');

INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000003', 'Sophia', 'Brown', '4567890123', '789 Elm Rd', '1995-07-20');

INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000004', 'Olivia', 'Williams', '8901234567', '567 Pine St', '1992-03-28');

INSERT INTO customer (id, first_name, last_name, phone, address, birthday)
VALUES ('CUS000005', 'Aiden', 'Davis', '2345678901', '901 Maple Ave', '1998-12-05');

-- Order insertions
INSERT INTO cart (id, date, net, tax, total, payment_method, status, customer, employee)
VALUES (gen_random_uuid(), '2023-01-15', 100.00, 10.00, 110.00, 'CASH', 'IN_PROGRESS', 'CUS000001', 'EMP000001');

INSERT INTO cart (id, date, net, tax, total, payment_method, status, customer, employee)
VALUES (gen_random_uuid(), '2023-02-20', 50.00, 5.00, 55.00, 'CREDIT_CARD', 'IN_PROGRESS', 'CUS000002', 'EMP000002');

INSERT INTO cart (id, date, net, tax, total, payment_method, status, customer, employee)
VALUES (gen_random_uuid(), '2023-03-10', 80.00, 8.00, 88.00, 'DEBT_CARD', 'IN_PROGRESS', 'CUS000003', 'EMP000003');

INSERT INTO cart (id, date, net, tax, total, payment_method, status, customer, employee)
VALUES (gen_random_uuid(), '2023-04-05', 120.00, 12.00, 132.00, 'CASH', 'IN_PROGRESS', 'CUS000004', 'EMP000004');

INSERT INTO cart (id, date, net, tax, total, payment_method, status, customer, employee)
VALUES (gen_random_uuid(), '2023-05-12', 70.00, 7.00, 77.00, 'CREDIT_CARD', 'IN_PROGRESS', 'CUS000005', 'EMP000005');



