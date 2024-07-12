create database loyalty;

\c loyalty;

create schema loyalty;

create type loyalty.loyalty_status as enum (
  'BRONZE',
  'SILVER',
  'GOLD'
);

create table loyalty.loyalty (
    id int8 generated always as identity primary key,
    username text not null unique,
    reservation_count int not null default 0,
    status loyalty.loyalty_status not null default 'BRONZE',
    discount int not null default 5
);

insert into loyalty.loyalty(username, reservation_count, status, discount)
values ('Test Max', 25, 'GOLD', 10);

------------

grant connect on database loyalty to master;

grant all on schema loyalty to master;
grant all on all tables in schema loyalty to master;
grant all on all sequences in schema loyalty to master;
grant all on all routines in schema loyalty to master;

alter default privileges in schema loyalty grant all on tables to master;
alter default privileges in schema loyalty grant all on functions to master;
alter default privileges in schema loyalty grant all on routines to master;
alter default privileges in schema loyalty grant all on types to master;
alter default privileges in schema loyalty grant all on sequences to master;

alter default privileges in schema loyalty grant select on tables to master;
