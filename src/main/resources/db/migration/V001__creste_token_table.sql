CREATE SEQUENCE token_table_sequence START 1 INCREMENT 1;

CREATE TABLE if not exists token_table(
    id int8 primary key not null,
    token varchar
);