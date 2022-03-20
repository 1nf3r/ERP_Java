DROP DATABASE IF EXISTS SALES_4_US;
CREATE DATABASE SALES_4_US;
USE SALES_4_US;

CREATE TABLE user (
id INT NOT NULL auto_increment PRIMARY KEY,
first_name VARCHAR (20) NOT NULL,
last_name VARCHAR (50) NOT NULL,
dni VARCHAR (9) NOT NULL,
email VARCHAR (45) NOT NULL,
phone INT NOT NULL,
password VARCHAR (200) NOT NULL,
address VARCHAR (60) NOT NULL,
rol VARCHAR (10) NOT NULL
);

CREATE TABLE purchase (
id INT NOT NULL auto_increment PRIMARY KEY,
DATE DATETIME NOT NULL,
total_prize DECIMAL NOT NULL,
user_id INT NOT NULL,
FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE provider (
id INT NOT NULL auto_increment PRIMARY KEY,
NAME VARCHAR (40) NOT NULL,
direction VARCHAR (120) NOT NULL,
phone INT NOT NULL,
website VARCHAR (40) NOT NULL,
email VARCHAR (40) NOT NULL
);

CREATE TABLE product(
id INT NOT NULL auto_increment PRIMARY KEY,
provider_id INT NOT NULL, 
name VARCHAR (20) NOT NULL,
prize INTEGER NOT NULL,
category VARCHAR (40) NOT NULL,
description VARCHAR (500) NOT NULL
);

CREATE TABLE linia_purchase (
purchase_id INT NOT NULL,
product_id INT NOT NULL,
unit INT NOT NULL,
PRIMARY KEY (purchase_id, product_id),
FOREIGN KEY (purchase_id) REFERENCES purchase (id),
FOREIGN KEY (product_id) REFERENCES product (ID)
);

CREATE TABLE opinion (
id INT NOT NULL auto_increment PRIMARY KEY,
user_id INT NOT NULL,
title VARCHAR (40) NOT NULL,
description VARCHAR (400) NOT NULL,
stars INT,
product_id INT NOT NULL,
FOREIGN KEY (product_id) REFERENCES product (id),
FOREIGN KEY (user_id) REFERENCES user (id)
);


/*CREATE TABLE rol (
id_rol int NOT NULL AUTO_INCREMENT,
name varchar(45) DEFAULT NULL,
user_id int DEFAULT NULL,
PRIMARY KEY (id_rol),
KEY fkRolUsuari_idx (user_id),
FOREIGN KEY (user_id) REFERENCES user (id)
); */


INSERT INTO user (first_name, last_name, dni, phone, password, email,address,rol) VALUES ("jose", "miranda", "12345678Z", 608262020, "$2a$10$N2Gyo4ryYoCZNS3im0Q/uO1N2FpXDiTsYaF72e/T1RUmnNwbG08hW", "jose@jose.local", "Al carrer","admin");
#INSERT INTO user (user_name, password, email) VALUES ("client", "$2a$10$Sul1phyB7QsHOmDafAeZP.fCFi/sAQLEoJyGG3nRdx0IV/uuPLn36", "client@client.local");
INSERT INTO user (first_name, last_name, dni, phone, password, email,address,rol) VALUES ("lucas", "padilla", "12345678P", 633568742, "$2a$10$CLOcU1vc/5Oq384KjEooLOCSO2o5xMnc2w4uaCIiKgGqP8PNGWRQW", "lucas@lucas.local", "Arriba", "client");

/*INSERT INTO rol(name, user_id) VALUES ('admin', 1);
INSERT INTO rol(name, user_id) VALUES ('client', 2);
#INSERT INTO rol(name, user_id) VALUES ('admin',3); */

INSERT INTO product (id, provider_id, name, prize, category, description) VALUES (1, 1, "Nike Air Force", 89.90, "Footwear", "The glow lives on in the Nike Air Force 1 '07, a basketball icon that brings a new twist to its already signature flawless leather, bold colors and the perfect amount of reflective.");
INSERT INTO product (id, provider_id, name, prize, category, description) VALUES (2, 2, "Jordan Retro 4", 120.90, "Footwear", "The glow lives on in the Nike Air Force 1 '07, a basketball icon that brings a new twist to its already signature flawless leather, bold colors and the perfect amount of reflective.");
INSERT INTO product (id, provider_id, name, prize, category, description) VALUES (3, 3, "Adidas Superstar", 120.90, "Footwear", "The glow lives on in the Nike Air Force 1 '07, a basketball icon that brings a new twist to its already signature flawless leather, bold colors and the perfect amount of reflective.");
INSERT INTO product (id, provider_id, name, prize, category, description) VALUES (4, 4, "Adidas Stan Smith", 99.90, "Footwear", "The glow lives on in the Nike Air Force 1 '07, a basketball icon that brings a new twist to its already signature flawless leather, bold colors and the perfect amount of reflective.");
INSERT INTO product (id, provider_id, name, prize, category, description) VALUES (5, 5, "Vans U Old SKOOL", 40.90, "Footwear", "The glow lives on in the Nike Air Force 1 '07, a basketball icon that brings a new twist to its already signature flawless leather, bold colors and the perfect amount of reflective.");

SELECT * FROM user;
SELECT * FROM product;