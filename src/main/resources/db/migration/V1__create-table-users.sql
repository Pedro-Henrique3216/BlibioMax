create table users(
    id UUID default RANDOM_UUID() primary key ,
    email varchar(255) not null unique ,
    senha varchar(200) not null,
    role varchar(200) not null
);