--liquibase formatted sql

--changeset vgolovnin:1.1
--comment: Create schema.
create schema delivery;
--rollback drop schema delivery;

--changeset vgolovnin:1.2
--comment: Create couriers table.
create table couriers
(
    id              uuid primary key,
    name            varchar(50) not null,
    transport_name  varchar(30) not null,
    transport_speed smallint    not null,
    location_x      smallint    not null,
    location_y      smallint    not null,
    free            boolean     not null
);

--changeset vgolovnin:1.3
--comment: Create orders table.
create table orders
(
    id         uuid primary key,
    status     varchar(20) not null,
    location_x smallint    not null,
    location_y smallint    not null,
    courier_id uuid default null references couriers (id)
);
