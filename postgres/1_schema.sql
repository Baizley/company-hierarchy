DROP TABLE IF EXISTS hierarchy;
CREATE TABLE hierarchy (
    id serial PRIMARY KEY,
    parent_id integer
);