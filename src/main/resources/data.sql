INSERT INTO categories (name, parent_id) VALUES ('Електроніка', NULL);
INSERT INTO categories (name, parent_id) VALUES ('Смартфони', 1);
INSERT INTO categories (name, parent_id) VALUES ('Ноутбуки', 1);

INSERT INTO products (name, description, price, category_id) VALUES ('iPhone 13', 'Apple Smartphone', 25000.00, 2);
INSERT INTO products (name, description, price, category_id) VALUES ('MacBook Pro', 'Laptop for devs', 80000.00, 3);