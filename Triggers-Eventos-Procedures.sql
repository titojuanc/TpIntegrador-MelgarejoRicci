
/*2A*/
DELIMITER //
CREATE TRIGGER chequearAgendaAntesDeCompra
BEFORE INSERT ON compra
FOR EACH ROW
BEGIN

 -- v es de verificacion, vefify, etc
  DECLARE v_es_servicio INT DEFAULT 0;
  DECLARE v_dia INT;
  DECLARE v_count INT DEFAULT 0;
  SELECT COUNT(*) INTO v_es_servicio FROM servicio WHERE id_publicacion = NEW.id_publicacion;
  IF v_es_servicio > 0 THEN
    SET v_dia = DAYOFWEEK(NEW.fecha);
    SELECT COUNT(*) INTO v_count FROM servicio_has_dia shd WHERE shd.id_servicio = NEW.id_publicacion AND shd.id_dia = v_dia;

    IF v_count = 0 THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El servicio no se ofrece en el dia solicitado.';
    END IF;

    -- Verificar si el usuario tiene una reserva en la misma fecha
    SELECT COUNT(*) INTO v_count
      FROM calendario c
      WHERE c.id_usuario = NEW.id_usuario
        AND DATE(c.fecha) = NEW.fecha;  -- comparar por fecha

    IF v_count > 0 THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Agenda ocupada: ya tiene una reserva en esa fecha.';
    END IF;
  END IF;
END;
//
DELIMITER ;


/*Punto 2B*/
DELIMITER //
CREATE TRIGGER actualizarNivelYStockDespuesDeCompra
AFTER INSERT ON compra
FOR EACH ROW
BEGIN
  DECLARE v_es_producto INT DEFAULT 0;
  DECLARE v_es_servicio INT DEFAULT 0;
  DECLARE v_vendedor INT;
  DECLARE v_nuevos_puntos_comprador INT;
  DECLARE v_nuevos_puntos_vendedor INT;
  DECLARE v_nivel_id INT;

  SELECT COUNT(*) INTO v_es_producto
  FROM producto
  WHERE id_publicacion = NEW.id_publicacion;

  SELECT COUNT(*) INTO v_es_servicio
  FROM servicio
  WHERE id_publicacion = NEW.id_publicacion;

  SELECT id_usuario INTO v_vendedor
  FROM publicacion
  WHERE id = NEW.id_publicacion;

  IF v_es_producto > 0 THEN
      UPDATE producto
      SET stock = stock - NEW.cantidad
      WHERE id_publicacion = NEW.id_publicacion;

      IF (SELECT stock FROM producto WHERE id_publicacion = NEW.id_publicacion) < 0 THEN
          SIGNAL SQLSTATE '45000'
          SET MESSAGE_TEXT = 'Stock insuficiente para completar la compra.';
      END IF;
  END IF;

  SET v_nuevos_puntos_comprador =
      COALESCE((SELECT puntos_acumulados FROM usuario WHERE id = NEW.id_usuario),0)
      + (10 * NEW.cantidad);

  UPDATE usuario
  SET puntos_acumulados = v_nuevos_puntos_comprador
  WHERE id = NEW.id_usuario;

  SET v_nuevos_puntos_vendedor =
      COALESCE((SELECT puntos_acumulados FROM usuario WHERE id = v_vendedor),0)
      + (10 * NEW.cantidad);

  UPDATE usuario
  SET puntos_acumulados = v_nuevos_puntos_vendedor
  WHERE id = v_vendedor;

  SELECT id INTO v_nivel_id
  FROM nivel
  WHERE puntos_minimos <= (SELECT puntos_acumulados FROM usuario WHERE id = NEW.id_usuario)
  ORDER BY puntos_minimos DESC
  LIMIT 1;

  IF v_nivel_id IS NOT NULL THEN
      UPDATE usuario
      SET id_nivel = v_nivel_id
      WHERE id = NEW.id_usuario;
  END IF;

  SELECT id INTO v_nivel_id
  FROM nivel
  WHERE puntos_minimos <= (SELECT puntos_acumulados FROM usuario WHERE id = v_vendedor)
  ORDER BY puntos_minimos DESC
  LIMIT 1;

  IF v_nivel_id IS NOT NULL THEN
      UPDATE usuario
      SET id_nivel = v_nivel_id
      WHERE id = v_vendedor;
  END IF;

END;
//
DELIMITER ;



/*Punto 2C*/
DELIMITER //
CREATE PROCEDURE adminPublicarContenido
(
    IN nombrePublicacion VARCHAR(255),
    IN descripcion VARCHAR(255),
    IN fechaPublicacion DATE,
    IN precioPublicacion DECIMAL(10,2),
    IN idVendedor INT,
    IN estado VARCHAR(45),

    IN tipoPublicacion ENUM('PRODUCTO','SERVICIO'),

    IN garantia INT,
    IN stockProducto INT,
    IN idCategoria INT,
    IN usado TINYINT,

    IN frecuencia VARCHAR(45),
    IN diasServicio VARCHAR(255),

    OUT mensaje TEXT
)
main_block: BEGIN
    DECLARE esAdmin TINYINT;
    DECLARE nuevoId INT;

    DECLARE pos INT DEFAULT 0;
    DECLARE nextPos INT DEFAULT 0;
    DECLARE dia VARCHAR(10);

    -- Verificar admin
    SELECT es_admin INTO esAdmin
    FROM usuario
    WHERE usuario.id = idVendedor;

    IF esAdmin <> 1 THEN
        SET mensaje = 'No se pudo cargar la publicación: el vendedor no es administrador.';
        LEAVE main_block;   -- ✔ FUNCIONA
    END IF;

    -- Insertar PUBLICACION
    INSERT INTO publicacion(nombre, descripcion, fecha_publicacion, precio, id_usuario, estado)
    VALUES (nombrePublicacion, descripcion, fechaPublicacion, precioPublicacion, idVendedor, estado);

    SET nuevoId = LAST_INSERT_ID();

    -- PRODUCTO
    IF tipoPublicacion = 'PRODUCTO' THEN

        INSERT INTO producto(id_publicacion, garantia, stock, id_categoria, usado)
        VALUES (nuevoId, garantia, stockProducto, idCategoria, usado);

        SET mensaje = 'Producto cargado con éxito.';

    -- SERVICIO
    ELSEIF tipoPublicacion = 'SERVICIO' THEN

        INSERT INTO servicio(id_publicacion, frecuencia)
        VALUES (nuevoId, frecuencia);

        -- Procesar días
        IF diasServicio IS NOT NULL AND diasServicio <> '' THEN

            SET pos = 1;

            loopDias: LOOP
                SET nextPos = LOCATE(',', diasServicio, pos);

                IF nextPos = 0 THEN
                    SET dia = SUBSTRING(diasServicio, pos);
                ELSE
                    SET dia = SUBSTRING(diasServicio, pos, nextPos - pos);
                END IF;

                IF TRIM(dia) <> '' THEN
                    INSERT INTO servicio_has_dia(id_servicio, id_dia)
                    VALUES (nuevoId, CAST(TRIM(dia) AS UNSIGNED));
                END IF;

                IF nextPos = 0 THEN
                    LEAVE loopDias;
                END IF;

                SET pos = nextPos + 1;
            END LOOP loopDias;

        END IF;

        SET mensaje = 'Servicio cargado con éxito.';

    ELSE
        SET mensaje = 'Error: tipoPublicacion inválido.';
    END IF;

END main_block //
DELIMITER ;

/*Punto 2D*/
DELIMITER //
CREATE PROCEDURE comprasPorUsuarioEnCiertoPeriodo(
    IN fecha1 DATE,
    IN fecha2 DATE,
    IN tipo TINYINT  -- 0 todos, 1 productos, 2 servicios
)
BEGIN
  SELECT u.id AS id_usuario, u.nombre, u.apellido, COUNT(c.id) AS cantidad FROM compra c
  JOIN publicacion p ON c.id_publicacion = p.id
  JOIN usuario u ON c.id_usuario = u.id
  WHERE c.fecha BETWEEN fecha1 AND fecha2
  AND (
      tipo = 0
      OR (tipo = 1 AND EXISTS (SELECT 1 FROM producto pr WHERE pr.id_publicacion = p.id))
      OR (tipo = 2 AND EXISTS (SELECT 1 FROM servicio s WHERE s.id_publicacion = p.id))
      )
  GROUP BY u.id, u.nombre, u.apellido
  ORDER BY cantidad DESC;
END;
//
DELIMITER ;

/*Punto 2E*/
DELIMITER //

CREATE PROCEDURE generarNotificacionesProximas24h()
BEGIN
  -- Inserta notificaciones para reservas en próximas 24 horas.
  INSERT INTO notificaciones (mensaje, id_usuario)
  SELECT CONCAT('Recordatorio: tienes una reserva para el servicio el ', DATE_FORMAT(c.fecha, '%Y-%m-%d %H:%i')) AS mensaje,
         c.id_usuario
  FROM calendario c
  WHERE c.fecha BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 24 HOUR)
    AND NOT EXISTS (
      SELECT 1 FROM notificaciones n
      WHERE n.id_usuario = c.id_usuario
        AND n.mensaje = CONCAT('Recordatorio: tienes una reserva para el servicio el ', DATE_FORMAT(c.fecha, '%Y-%m-%d %H:%i'))
    );
END;
//

-- Evento programado que corre todos los días a las 08:00 del servidor
CREATE EVENT crear_notificaciones_08 ON SCHEDULE EVERY 1 DAY
STARTS CONCAT(CURRENT_DATE, ' 08:00:00')
DO
  CALL generarNotificacionesProximas24h();
  
DELIMITER ;

/*2F*/
DELIMITER //
CREATE EVENT actualizar_publicaciones_viejas ON SCHEDULE EVERY 1 DAY
DO
BEGIN
  UPDATE publicacion
  SET estado = 'Pendiente de revision'
  WHERE fecha_publicacion < DATE_SUB(CURRENT_DATE, INTERVAL 6 MONTH)
    AND estado <> 'Pendiente de revision';
END;
//
DELIMITER ;

    
	