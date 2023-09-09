
CREATE USER major WITH LOGIN PASSWORD 'password';

CREATE DATABASE citylist;

GRANT ALL PRIVILEGES ON DATABASE citylist TO major;

connect citylist;

CREATE SCHEMA portal AUTHORIZATION major;
