CREATE DATABASE songdb;
\c songdb;

CREATE TABLE songs (
  id TEXT PRIMARY KEY,
  name varchar(100) NOT NULL,
  artist varchar(100) NOT NULL,
  album varchar(100) NOT NULL,
  duration varchar(5) NOT NULL,
  year varchar(4) NOT NULL
);