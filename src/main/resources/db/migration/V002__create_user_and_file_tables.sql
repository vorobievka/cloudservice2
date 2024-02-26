CREATE SEQUENCE user_sequence START 2 INCREMENT 1;

CREATE TABLE if not exists user_table(
    id int8 primary key not null,
    email varchar,
    password varchar,
    first_name varchar,
    last_name varchar,
    token_id int8 references token_table(id)

);

insert into user_table(id, email, password, first_name, last_name)
values (1, 'petrov@ads.rh', '1234asa', 'Павел', 'Петров'),
(2, 'test', 'test', 'test', 'test');


CREATE SEQUENCE file_sequence START 1 INCREMENT 1;

CREATE TABLE if not exists file_table(
    id int8 primary key not null,
    file_name varchar,
    size int8,
    type varchar,
    body bytea

);