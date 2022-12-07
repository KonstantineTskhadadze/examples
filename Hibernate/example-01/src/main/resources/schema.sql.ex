CREATE TABLE Students (
student_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
name VARCHAR(100) NOT NULL,
email VARCHAR(100),
creation_date TIMESTAMP,
Unique (name)
);
CREATE TABLE Game (id BIGINT NOT NULL, name VARCHAR(255), PRIMARY KEY (id));
CREATE TABLE Player (id BIGINT NOT NULL, name VARCHAR(255), game_id BIGINT, PRIMARY KEY (id), FOREIGN KEY (game_id) REFERENCES Game(id));

CREATE TABLE Account (accountNumber VARCHAR(255), accountType VARCHAR(255), description VARCHAR(255), PRIMARY KEY (accountNumber, accountType));
CREATE TABLE Book (title VARCHAR(255), language VARCHAR(255), description VARCHAR(255), PRIMARY KEY (title, language));
