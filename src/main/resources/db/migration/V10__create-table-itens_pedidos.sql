create table itens_pedidos(
    livro_id bigint,
    pedido_id bigint,
    quantidade int not null ,
    valor double not null,
    primary key (livro_id, pedido_id),
    foreign key (livro_id) references livros(id),
    foreign key (pedido_id) references pedidos(id)
);