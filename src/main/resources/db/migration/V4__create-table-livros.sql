create table livros(
    id bigint auto_increment primary key ,
    titulo varchar(255) not null unique ,
    autor varchar(255) not null ,
    quantidade int not null,
    editora varchar(255) not null
);