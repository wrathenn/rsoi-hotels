create extension if not exists pgcrypto;

create role master
nosuperuser
valid until 'infinity';

create user backend in role master password 'backend';
