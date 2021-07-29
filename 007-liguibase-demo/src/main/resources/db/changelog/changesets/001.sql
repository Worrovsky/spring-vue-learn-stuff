--liquibase formatted sql

--changeset nvoxland:2

create table user
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);


