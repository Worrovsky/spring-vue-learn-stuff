-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

--liquibase formatted sql

--changeset worrovsky:1

create table user (id bigint not null, name varchar(255), primary key (id))