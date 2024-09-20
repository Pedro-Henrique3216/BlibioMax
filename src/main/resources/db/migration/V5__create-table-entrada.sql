create table entrada (
    numero_da_nota decimal(10) primary key ,
    bibliotecario_id bigint,
    data_entrada DATE not null,
    valor_total decimal(5, 2),
    foreign key (bibliotecario_id) references bibliotecarios(id)
);
