create table topicos(
    id bigint not null auto_increment,
    titulo varchar(200) not null,
    mensaje text not null,
    autor varchar(200) not null,
    curso varchar(200) not null,

    primary key (id)
);