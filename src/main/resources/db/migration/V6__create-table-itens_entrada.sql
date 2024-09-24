CREATE TABLE itens_entrada (
        id bigint auto_increment primary key,
        entrada_id BIGINT not null ,
        preco decimal(10, 2) not null,
        quantidade INT not null,
        valor_total DECIMAL(10, 2) not null,
        livro_id BIGINT not null ,
        FOREIGN KEY (livro_id) REFERENCES livros(id),
        foreign key (entrada_id) references entrada(numero_da_nota)
);