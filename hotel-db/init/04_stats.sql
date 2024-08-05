create database stats;

\c stats;

create schema stats;

create table stats.stats (
    id int8 generated always as identity primary key,
    ts timestamptz not null,
    data jsonb not null
);

create index stats$i_data on stats.stats using gin(data);

------------

grant connect on database stats to master;

grant all on schema stats to master;
grant all on all tables in schema stats to master;
grant all on all sequences in schema stats to master;
grant all on all routines in schema stats to master;

alter default privileges in schema stats grant all on tables to master;
alter default privileges in schema stats grant all on functions to master;
alter default privileges in schema stats grant all on routines to master;
alter default privileges in schema stats grant all on types to master;
alter default privileges in schema stats grant all on sequences to master;

alter default privileges in schema stats grant select on tables to master;
