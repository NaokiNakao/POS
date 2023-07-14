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
