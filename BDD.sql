    create database TPMercadoTecnico;
    use TPMercadoTecnico;

    create table nivel (
        id int primary key,
        nombre varchar(50) not null,
        descuento decimal(5,2) not null check (descuento >= 0 and descuento <= 100)
    );

    create table usuario (
        id int primary key auto_increment,
        nombre varchar(100) not null,
        apellido varchar(100) not null,
        email varchar(100) not null unique,
        contrasena varchar(100) not null,
        puntos_acumulados int,
        es_admin boolean not null default false,
        id_nivel int,
        foreign key (id_nivel) references nivel(id)
    );

    create table calendario (
        id int primary key auto_increment,
        fecha datetime not null,    /*se va a rellenar con la fecha y hora decidida por el usuario mientars compra el servicio*/
        motivo varchar(255) not null,
        id_usuario int,
        foreign key (id_usuario) references usuario(id)
    );

    create table notificaciones (
        id int primary key auto_increment,
        mensaje varchar(255) not null,
        id_usuario int,
        foreign key (id_usuario) references usuario(id)
    );

    create table publicacion (
        id int primary key auto_increment,
        nombre varchar(255) not null,
        descripcion varchar(255) not null,
        fecha_publicacion date not null,
        precio decimal(10,2) not null,
        stock int not null,
        id_usuario int,
        foreign key (id_usuario) references usuario(id)
    );

    create table servicio (
        id_publicacion int primary key,
        foreign key (id_publicacion) references publicacion(id)
    );

    create table dias (
        id_publicacion int primary key,
        foreign key (id_publicacion) references servicio(id_publicacion),
        dia int not null check (dia between 1 and 7)
    );

    create table producto (
        id_publicacion int primary key,
        foreign key (id_publicacion) references publicacion(id),
        garantia int
    );

    create table compra (
        id int primary key auto_increment,
        fecha date not null,
        total decimal(10,2) not null,
        id_usuario int,
        foreign key (id_usuario) references usuario(id),
        id_publicacion int,
        foreign key (id_publicacion) references publicacion(id)
    );