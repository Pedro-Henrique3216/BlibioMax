create table pedidos(
    id bigint auto_increment primary key,
    pessoa_id bigint,
    foreign key (pessoa_id) references pessoas(id),
    tipo_pedido varchar(100) not null,
    valor double not null,
    status_pedido varchar(100) not null,
    date_pedido date not null
);