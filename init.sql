create table person(
    person_id serial primary key,
    name text not null,
    birth_year int not null
);

insert into person (name, birth_year) values
('niels', 1988),
('erik', 2019),
('laura', 1988),
('marilyn', 1926),
('abraham', 1809)
;

