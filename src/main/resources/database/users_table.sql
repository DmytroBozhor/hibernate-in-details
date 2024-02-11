CREATE TABLE IF NOT EXISTS users
(
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    username   VARCHAR(128) UNIQUE,
    role       VARCHAR(32),
    PRIMARY KEY (firstname, lastname, birth_date)
);

DROP TABLE IF EXISTS users;