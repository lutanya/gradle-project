CREATE DATABASE resourcedb;
\c resourcedb;

CREATE TABLE songs (
  id SERIAL PRIMARY KEY,
  content BYTEA NOT NULL
);