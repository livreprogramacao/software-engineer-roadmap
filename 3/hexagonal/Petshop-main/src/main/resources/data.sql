-- Address
INSERT INTO address (street, number, city, state, zip_code, adress_type) VALUES ('Rua das Flores', '123', 'São Paulo', 'SP', '01001000', 'HOME');
INSERT INTO address (street, number, city, state, zip_code, adress_type) VALUES ('Av. Brasil', '456', 'Rio de Janeiro', 'RJ', '20040020', 'JOB');
INSERT INTO address (street, number, city, state, zip_code, adress_type) VALUES ('Rua da Paz', '789', 'Belo Horizonte', 'MG', '30130000', 'HOME');
INSERT INTO address (street, number, city, state, zip_code, adress_type) VALUES ('Av. Paulista', '1000', 'São Paulo', 'SP', '01310100', 'OTHERS');
INSERT INTO address (street, number, city, state, zip_code, adress_type) VALUES ('Rua do Sol', '200', 'Salvador', 'BA', '40020000', 'HOME');

-- Customer
INSERT INTO customer (name, phone, cpf, born_date, created_at, address_id) VALUES ('João Silva', '11999998888', '12345678901', '1990-05-15', CURRENT_DATE, 1);
INSERT INTO customer (name, phone, cpf, born_date, created_at, address_id) VALUES ('Maria Santos', '21988887777', '98765432100', '1985-10-20', CURRENT_DATE, 2);
INSERT INTO customer (name, phone, cpf, born_date, created_at, address_id) VALUES ('Pedro Oliveira', '31977776666', '45678912300', '1992-03-25', CURRENT_DATE, 3);
INSERT INTO customer (name, phone, cpf, born_date, created_at, address_id) VALUES ('Ana Costa', '11966665555', '32165498700', '1988-12-10', CURRENT_DATE, 4);
INSERT INTO customer (name, phone, cpf, born_date, created_at, address_id) VALUES ('Lucas Ferreira', '71955554444', '78912345600', '1995-07-30', CURRENT_DATE, 5);

-- Product
INSERT INTO product (name, description, category, price) VALUES ('Ração Premium', 'Ração para cães adultos', 'FOOD', 89.90);
INSERT INTO product (name, description, category, price) VALUES ('Osso de Borracha', 'Brinquedo resistente para cães', 'TOY', 24.50);
INSERT INTO product (name, description, category, price) VALUES ('Coleira de Couro', 'Coleira ajustável para cães', 'ACCESSORY', 45.00);
INSERT INTO product (name, description, category, price) VALUES ('Vermífugo', 'Medicamento antiparasitário', 'MEDICINE', 32.70);
INSERT INTO product (name, description, category, price) VALUES ('Ração Úmida', 'Sachê para gatos', 'FOOD', 8.90);
INSERT INTO product (name, description, category, price) VALUES ('Bola com Sino', 'Brinquedo interativo para gatos', 'TOY', 15.00);
INSERT INTO product (name, description, category, price) VALUES ('Peitoral Ajustável', 'Peitoral para passeios', 'ACCESSORY', 38.00);
INSERT INTO product (name, description, category, price) VALUES ('Antipulgas', 'Tratamento mensal para cães', 'MEDICINE', 55.90);
INSERT INTO product (name, description, category, price) VALUES ('Petisco Natural', 'Snack saudável para cães', 'FOOD', 12.50);
INSERT INTO product (name, description, category, price) VALUES ('Rato de Pelúcia', 'Brinquedo macio para gatos', 'TOY', 18.00);

-- Orders
INSERT INTO orders (customer_name, quantity, product_id, status) VALUES ('João Silva', 2, 1, 'PENDING');
INSERT INTO orders (customer_name, quantity, product_id, status) VALUES ('Maria Santos', 1, 3, 'PROCESSING');
INSERT INTO orders (customer_name, quantity, product_id, status) VALUES ('Pedro Oliveira', 3, 5, 'COMPLETED');
INSERT INTO orders (customer_name, quantity, product_id, status) VALUES ('Ana Costa', 1, 7, 'FAILED');
INSERT INTO orders (customer_name, quantity, product_id, status) VALUES ('Lucas Ferreira', 2, 9, 'PENDING');
