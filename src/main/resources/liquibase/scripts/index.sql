--liquibase formatted sql

--changeset ann:1

drop index st_name_idx;

--changeset ann:2

drop index  ofc_name_color_idx;


--changeset ann:3

create index st_name_idx on students (name);

--changeset ann:4

create index  ofc_name_color_idx on faculty (name, color);

