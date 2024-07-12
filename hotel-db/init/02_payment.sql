create database payment;

\c payment;

create schema payment;

create type payment.payment_status as enum (
  'PAID',
  'CANCELED'
);

create table payment.payment (
    id int8 generated always as identity primary key,
    payment_uid uuid not null,
    status payment.payment_status not null,
    price int not null
);

------------

grant connect on database payment to master;

grant all on schema payment to master;
grant all on all tables in schema payment to master;
grant all on all sequences in schema payment to master;
grant all on all routines in schema payment to master;

alter default privileges in schema payment grant all on tables to master;
alter default privileges in schema payment grant all on functions to master;
alter default privileges in schema payment grant all on routines to master;
alter default privileges in schema payment grant all on types to master;
alter default privileges in schema payment grant all on sequences to master;

alter default privileges in schema payment grant select on tables to master;
