CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32)
);

CREATE SEQUENCE users_id_seq OWNED BY public.users.id;

DROP TABLE IF EXISTS users;