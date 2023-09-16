alter table usuarios add (status tinyint not null);
update usuarios set status = 1;