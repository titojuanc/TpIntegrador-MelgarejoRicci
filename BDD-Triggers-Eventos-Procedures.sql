/*Punto 2A, PENDIENTE*/
/*delimiter //
create trigger chequearAgenda before insert on TPMercadoTecnico.compra for each row
begin
    IF EXISTS (SELECT id_publicacion FROM servicio WHERE id_publicacion = NEW.id_publicacion) THEN
    end if;
end//*/

/*Punto 2B*/
delimiter //
create trigger actualizarNivel after insert on TPMercadoTecnico.compra for each row
begin
    /*id_usuario publicacion es de quien lo publica
    id_usuario de compra es de quien la compra*/
    
    IF EXISTS (SELECT id_publicacion FROM producto WHERE id_publicacion = NEW.id_publicacion) THEN
    
    /*Update comprador*/
    update usuario set puntos_acumulados = puntos_acumulados + 10 where id = new.id_usuario;
    
    /*Update vendedor*/
    update usuario set puntos_acumulados = puntos_acumulados + 10 where id = 
    (select id_usuario from publicacion where publicacion.id = new.id_publicacion);
    
    end if;
end//

/*Punto 2C*/
delimiter //
create procedure adminPublicarContenido (in nombrePublicacion varchar(255)
, in descripcion varchar(255), in fechaPublicacion date, in precioPublicacion DECIMAL, in stockDisponible 
int, in idVendedor int, in estado varchar(45), out mensaje text) 
begin
	declare esAdmin TINYINT;
    select es_admin into esAdmin from usuario where usuario.id = idVendedor;
    if esAdmin = 1 then
		INSERT INTO publicacion (nombre,descripcion,fecha_publicacion,precio,stock,id_usuario,estado)
		VALUES (nombrePublicacion, descripcion, fechaPublicacion, precioPublicacion, stockDisponible, 
        idVendedor, estado);
        set mensaje="Publicacion cargada con exito";
	else 
		set mensaje="No se pudo cargar la publicacion ya que el vendedor no es administrador";
    end if;
end//
delimiter ;


/*Punto 2D*/
delimiter //
create procedure comprasPorUsuarioEnCiertoPeriodo(in fecha1 date, in fecha2 date, 
in contarServicios TINYINT, in contarProductos TINYINT, out contador int)
begin
	declare hayFilas boolean default 1;
    declare cantidadProductos int default 0;
    declare cantidadServicios int default 0;
    
    declare idCompraAux int;
    declare idPublicacionCompraAux int;
	declare comprasCursor cursor for select id, id_publicacion from compra where fecha >= fecha1 and fecha <= fecha2;
	
    declare continue handler for not found set hayFilas = 0;
	
    open comprasCursor;
	bucle:loop
		fetch comprasCursor into idCompraAux, idPublicacionCompraAux;
		if hayFilas = 0 then
			leave bucle;
		end if;
        
        if contarServicios = 1 then
			IF EXISTS (SELECT id_publicacion FROM servicio WHERE id_publicacion = idPublicacionCompraAux) THEN
				set cantidadServicios = cantidadServicios+1;
            end if;
		end if;
        
        if contarProductos = 1 then
			IF EXISTS (SELECT id_publicacion FROM producto WHERE id_publicacion = idPublicacionCompraAux) THEN
				set cantidadProductos = cantidadProductos+1;
            end if;
        end if;
		end loop bucle;
	close comprasCursor;
    set contador = cantidadProductos + cantidadServicios;
end//
delimiter //

/*Punto 2E*/
create procedure colocarNotificaciones(in hourAux datetime, out Mensaje text)
begin
	declare hayFilas boolean default 1;
	declare idPublicacion int;
    declare idComprador int;
    
	declare serviciosCursor cursor for select id_publicacion, id_usuario from compra where 
    id_publicacion in (select id_publicacion from servicio where id_publicacion in 
    (select id_servicio from calendario where fecha = DATE_ADD(hourAux, INTERVAL 24 HOUR))); 
    
	declare continue handler for not found set hayFilas = 0;
	open nombreCursor;
	bucle:loop
		fetch nombreCursor into variable1;
		if hayFilas = 0 then
			leave bucle;
		end if;
		- - codigo
	end loop bucle;
	close nombre_cursor;
end
delimiter //
create event crearNotificacionesEvento ON SCHEDULE EVERY 1 DAY STARTS TIMESTAMP(CURRENT_DATE, '08:00:00') do
begin
	declare horaAux datetime;
    set horaAux=TIMESTAMP(CURRENT_DATE, '08:00:00');
	call colocarNotificaciones(horaAux);
end//
delimiter //


/*2F*/
delimiter //
create event ponerEnEsperaPublicacionesViejas ON SCHEDULE EVERY 1 DAY DO
begin
	declare hayFilas boolean default 1;
    declare idAux int;
    declare fechaAux DATE;
    declare publicacionCursor cursor for select id, fecha_publicacion from publicacion;
    declare continue handler for not found set hayFilas = 0;
    
    open publicacionCursor;
    bucle:loop
		fetch publicacionCursor into idAux, fechaAux;
        if hayFilas = 0 then
			leave bucle;
		end if;
        if (fechaAux < DATE_SUB(CURRENT_DATE, INTERVAL 6 MONTH)) THEN
			update publicacion set estado = 'Pendiente de revision' where id=idAux;
		end if;
	end loop bucle;
    close publicacionCursor;
end;

delimiter ;
    
        
    
	