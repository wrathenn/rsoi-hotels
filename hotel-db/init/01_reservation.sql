create database reservation;

\c reservation;

create schema reservation;

create type reservation.reservation_status as enum (
  'PAID',
  'CANCELED'
);

create table reservation.hotel (
    id int8 generated always as identity primary key,
    hotel_uid uuid not null unique,
    name text not null,
    country text not null,
    city text not null,
    address text not null,
    stars int,
    price int not null
);

insert into reservation.hotel(hotel_uid, name, country, city, address, stars, price)
values ('049161bb-badd-4fa8-9d90-87c9a82b0668'::uuid, 'Ararat Park Hyatt Moscow', 'Россия', 'Москва', 'Неглинная ул., 4', 5, 10000);

create table reservation.reservation (
    id int8 generated always as identity primary key,
    reservation_uid uuid unique not null,
    username text not null,
    payment_uid uuid not null,
    hotel_id int not null references reservation.hotel (id),
    status reservation.reservation_status not null,
    start_date  timestamptz,
    end_date timestamptz
);

----------------------

grant connect on database reservation to master;

grant all on schema reservation to master;
grant all on all tables in schema reservation to master;
grant all on all sequences in schema reservation to master;
grant all on all routines in schema reservation to master;

alter default privileges in schema reservation grant all on tables to master;
alter default privileges in schema reservation grant all on functions to master;
alter default privileges in schema reservation grant all on routines to master;
alter default privileges in schema reservation grant all on types to master;
alter default privileges in schema reservation grant all on sequences to master;

alter default privileges in schema reservation grant select on tables to master;
