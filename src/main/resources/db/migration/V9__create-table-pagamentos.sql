create table pagamentos (
    id bigint auto_increment primary key,
    tipo varchar(20) not null,
    valor double not null,
    data_criacao date,
    data_pagamento date,
    pedido_id bigint,
    foreign key (pedido_id) references pedidos(id) on delete cascade
);