alter table topicos add (
    fecha_creacion datetime not null,
    status tinyint not null
);
update topicos set status = 1;