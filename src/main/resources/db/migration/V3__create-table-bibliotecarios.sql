create table bibliotecarios (
    id bigint ,
    foreign key(id) references pessoas(id),
    numero_registro int not null unique
);