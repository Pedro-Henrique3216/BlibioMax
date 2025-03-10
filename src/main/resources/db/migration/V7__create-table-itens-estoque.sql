create table itens_estoque(
    id bigint auto_increment primary key,
    livro_id bigint,
    quantidade int,
    minimo_quantidade int,
    foreign key (livro_id) references livros(id),
    version BIGINT
)