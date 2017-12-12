create database if not exists citydb;

use citydb;

CREATE USER IF NOT EXISTS 'test'@'%' IDENTIFIED BY 'welcome1';
GRANT ALL ON citydb.* TO 'test'@'%';

CREATE TABLE city (
  name VARCHAR(255) NOT NULL PRIMARY KEY
) CHARACTER SET utf8
  COLLATE utf8_general_ci;

INSERT INTO city (name) VALUES ("Amsterdam");
INSERT INTO city (name) VALUES ("Bristol");
INSERT INTO city (name) VALUES ("London");
INSERT INTO city (name) VALUES ("San Francisco");
