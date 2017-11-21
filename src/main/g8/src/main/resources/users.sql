CREATE DATABASE users;

CREATE TABLE api_user (
  id SERIAL PRIMARY KEY,
  username VARCHAR (100) NOT NULL UNIQUE,
  email VARCHAR (500) NOT NULL UNIQUE
);

INSERT INTO api_user (username, email) VALUES ('gvolpe', 'gvolpe@github.com');
