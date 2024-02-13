CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(128) UNIQUE,
    firstname  VARCHAR(128),
    lastname   VARCHAR(128),
    birth_date DATE,
    role       VARCHAR(32),
    company_id BIGINT REFERENCES company (id)
);

DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS company
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128)
);

DROP TABLE IF EXISTS company;

CREATE TABLE IF NOT EXISTS "department"
(
    "id"         BIGSERIAL PRIMARY KEY,
    "name"       VARCHAR(128),
    "capacity"   INT,
    "company_id" BIGINT REFERENCES company (id)
);

DROP TABLE IF EXISTS department;