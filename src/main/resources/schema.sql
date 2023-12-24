CREATE DATABASE IF NOT EXISTS invoice;

CREATE TABLE clients (
	id INT PRIMARY KEY auto_increment,
    name VARCHAR(75),
    surname VARCHAR(75),
    doc_number VARCHAR(11)
);
CREATE TABLE products (
	id INT PRIMARY KEY auto_increment,
    description VARCHAR(150),
    code VARCHAR(50),
    stock INT,
    price DOUBLE
);
CREATE TABLE invoice (
    id INT PRIMARY KEY auto_increment,
    clients_id INT,
    created_at DATE,
    total DOUBLE,

    CONSTRAINT fk_clients_id FOREIGN KEY (clients_id) REFERENCES clients(id)
);
CREATE TABLE invoice_details (
    id INT PRIMARY KEY auto_increment,
    invoice_id INT,
    quantity INT,
    products_id INT,
    price DOUBLE,

    CONSTRAINT fk_invoice_id FOREIGN KEY (invoice_id) REFERENCES invoice(id),
    CONSTRAINT fk_products_id FOREIGN KEY (products_id) REFERENCES products(id)
);