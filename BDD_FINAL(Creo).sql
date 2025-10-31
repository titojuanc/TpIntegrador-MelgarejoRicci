create table reporte (
	id int primary key,
    mensaje varchar(45),
    id_usuario int,
    foreign key (id_usuario) references usuario (id),
    id_publicacion int,
    foreign key (id_publicacion) references publicacion (id)
);

create table calificacion (
	id int primary key,
    calificacion int,
	id_usuario int,
    foreign key (id_usuario) references usuario (id),
    id_publicacion int,
    foreign key (id_publicacion) references publicacion (id)
);

create table categoria (
	id int primary key,
    nombre varchar(45)
);

