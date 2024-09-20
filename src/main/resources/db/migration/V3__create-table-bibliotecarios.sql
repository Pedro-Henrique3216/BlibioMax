create table bibliotecarios (
    id bigint ,
    foreign key(id) references pessoas(id),
    primary key (id),
    numero_registro int not null unique
);