CREATE EXTENSION IF NOT EXISTS LTREE WITH SCHEMA public;

DROP TABLE IF EXISTS hierarchy;
CREATE TABLE hierarchy (
    id serial PRIMARY KEY,
    path ltree
);

CREATE INDEX IF NOT EXISTS path_index ON hierarchy USING gist (path);