-- Category insertions
INSERT INTO category (id, name) VALUES ('CAT000001', 'Building');
INSERT INTO category (id, name) VALUES ('CAT000002', 'Plumbing');
INSERT INTO category (id, name) VALUES ('CAT000003', 'Paint');
INSERT INTO category (id, name) VALUES ('CAT000004', 'Gardening');
INSERT INTO category (id, name) VALUES ('CAT000005', 'Power Tools');

-- Product insertions
INSERT INTO product (id, name, category, acquisition_cost, selling_price)
VALUES ('PRO000001', 'Drill', 'CAT000005', 43.99, 51.83);