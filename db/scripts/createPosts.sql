create table posts(
    id serial primary key,
    name text,
    text text,
    link text unique,
    created timestamp without time zone
);