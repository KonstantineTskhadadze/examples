CREATE TABLE address(id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, street VARCHAR(45), city VARCHAR(45));
CREATE TABLE users(id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY, username VARCHAR(45), address_id INTEGER, FOREIGN KEY(address_id) REFERENCES address(id), Unique (address_id));