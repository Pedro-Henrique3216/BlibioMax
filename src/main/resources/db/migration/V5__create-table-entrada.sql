create table entrada (
    numero_da_nota bigint primary key ,
    bibliotecario_id bigint,
    data_entrada DATE not null,
    valor_total decimal,
    status varchar(20),
    foreign key (bibliotecario_id) references bibliotecarios(id)
);
