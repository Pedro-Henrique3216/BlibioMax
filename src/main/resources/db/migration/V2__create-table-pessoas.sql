create table pessoas(
    id bigint auto_increment primary key ,
    nome varchar(255) not null ,
    telefone varchar(11) not null ,
    user_id UUID ,
    foreign key (user_id) references users(id) on delete cascade
);