package com.mercadotecnico.mr.mercadotecnico.service;

import com.mercadotecnico.mr.mercadotecnico.dto.*;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.stream.Stream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Service
public class PublicacionService {
    UserRepository bdd_usuarios;
    PublicacionRepository bdd_publicaciones;
    DiaReposiroty bdd_dias;
    ServicioRepository bdd_servicios;
    ProductoRepository bdd_productos;
    DiasServicioRepository bdd_diasDeServicio;
    CategoriaRepository bdd_categoria;
    CalendarioRepository bdd_calendario;
    CalificacionRepository bdd_calificacion;
    ReporteRepository bdd_reportes;

    public PublicacionService(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, DiaReposiroty bdd_dias, ServicioRepository bdd_servicios, ProductoRepository bdd_productos, DiasServicioRepository bdd_diasDeServicio, CategoriaRepository bdd_categoria, CalendarioRepository bdd_calendario, CalificacionRepository bdd_calificacion, ReporteRepository bdd_reportes) {
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_dias = bdd_dias;
        this.bdd_servicios = bdd_servicios;
        this.bdd_productos = bdd_productos;
        this.bdd_diasDeServicio = bdd_diasDeServicio;
        this.bdd_categoria = bdd_categoria;
        this.bdd_calendario = bdd_calendario;
        this.bdd_calificacion = bdd_calificacion;
        this.bdd_reportes = bdd_reportes;
    }

    public Publicacion actualizarEstado(Long id, String estado) throws Exception {
        Publicacion actualizar = bdd_publicaciones.findById(id).get();

        if (actualizar == null) {
            throw new Exception("No se encontró la especialidad con ID " + id);
        }

        actualizar.setEstado(estado);
        return bdd_publicaciones.save(actualizar);
    }

    public void crearPublicacion(Long idUsuario, PublicacionDTO dto) {
        System.out.println(dto);
        Usuario usuario = bdd_usuarios.findById(idUsuario).get();
        System.out.println(usuario.getNombre());
        Publicacion publicacion = new Publicacion(dto.getNombre(), dto.getDescripcion(), dto.getFechaPublicacion(), dto.getPrecio(), usuario, dto.getEstado());
        System.out.println(publicacion.getEstado());
        bdd_publicaciones.save(publicacion);
        if (dto.getTipo().equals("Producto")) {
            Producto producto = new Producto(publicacion, dto.getGarantia(), dto.getStock(), bdd_categoria.findById((long) dto.getCategoria()).get(), dto.isUsado());
            System.out.println(producto.getGarantia());
            bdd_productos.save(producto);
        } else if (dto.getTipo().equals("Servicio")) {
            Servicio servicio = new Servicio(publicacion, dto.getFrecuencia());
            bdd_servicios.save(servicio);
            for (Long d : dto.getDias()) {
                Dia diaAux = bdd_dias.findById(d).get();
                Servicio_has_dia diasdeservicio = new Servicio_has_dia(servicio, diaAux);
                System.out.println(diasdeservicio.getServicio().getPublicacion().getNombre());
                System.out.println(bdd_dias.findById(d).get().getDia());
                bdd_diasDeServicio.save(diasdeservicio);
            }
        }
    }

    private boolean filtrarPorPrecio(Publicacion pub, Double min, Double max) {
        double precio = pub.getPrecio();

        if (min != null && precio < min) return false;
        if (max != null && precio > max) return false;

        return true;
    }


    public List<Publicacion> filtrar(
            Boolean usado,
            Integer categoria,
            String tipo,
            String frecuencia,
            Double precioMin,
            Double precioMax
    ) {

        // --- Caso 1: No se especifica tipo → devolver todo pero con filtros de precio ---
        if (tipo == null) {
            return Stream.concat(
                    bdd_productos.findAll().stream()
                            .filter(prod -> filtrarPorPrecio(prod.getPublicacion(), precioMin, precioMax))
                            .map(Producto::getPublicacion),
                    bdd_servicios.findAll().stream()
                            .filter(serv -> filtrarPorPrecio(serv.getPublicacion(), precioMin, precioMax))
                            .map(Servicio::getPublicacion)
            ).toList();
        }

        // --- Caso 2: Filtrar productos ---
        if (tipo.equalsIgnoreCase("producto")) {
            return bdd_productos.findAll().stream()
                    .filter(prod -> usado == null || prod.isUsado() == usado)
                    .filter(prod -> categoria == null ||
                            (prod.getCategoria() != null &&
                                    prod.getCategoria().getId().equals(categoria.longValue())))
                    .filter(prod -> filtrarPorPrecio(prod.getPublicacion(), precioMin, precioMax))
                    .map(Producto::getPublicacion)
                    .toList();
        }

        // --- Caso 3: Filtrar servicios ---
        if (tipo.equalsIgnoreCase("servicio")) {
            return bdd_servicios.findAll().stream()
                    .filter(serv -> frecuencia == null ||
                            (serv.getFrecuencia() != null &&
                                    serv.getFrecuencia().equalsIgnoreCase(frecuencia)))
                    .filter(serv -> filtrarPorPrecio(serv.getPublicacion(), precioMin, precioMax))
                    .map(Servicio::getPublicacion)
                    .toList();
        }

        return List.of(); // tipo inválido
    }


    public Optional<Publicacion> obtenerPorId(Long id) {
        return bdd_publicaciones.findById(id);
    }

    public ResponseEntity<?> eliminarPublicacion(Long id) throws Exception {

        Optional<Publicacion> optionalPublicacion = bdd_publicaciones.findById(id);

        if (optionalPublicacion.isEmpty()) {
            return ResponseEntity.badRequest().body("No existe publicacion con ese ID");
        }

        Publicacion publicacion = optionalPublicacion.get();

        // Primero revisamos si es un servicio
        Optional<Servicio> optionalServicio = bdd_servicios.findById(id);

        if (optionalServicio.isPresent()) {

            Servicio servicio = optionalServicio.get();

            // Verificar si el servicio tiene calendario activo
            boolean tieneCalendarios = !bdd_calendario.findByServicio(servicio).isEmpty();

            if (tieneCalendarios) {

                actualizarEstado(id, "En pausa");
                return ResponseEntity.ok(
                        "La publicación se pausó porque hay usuarios que tienen contratado el servicio. " +
                                "Una vez finalizado, puede borrarse"
                );

            } else {
                // Eliminar el servicio y luego la publicación
                bdd_servicios.delete(servicio);
                bdd_publicaciones.delete(publicacion);

                return ResponseEntity.ok("Se borró con éxito");
            }

        } else {
            // Si no es servicio, asumimos que es producto
            Optional<Producto> optionalProducto = bdd_productos.findById(id);

            if (optionalProducto.isPresent()) {
                bdd_productos.delete(optionalProducto.get());
                bdd_publicaciones.delete(publicacion);
                return ResponseEntity.ok("Se borró con éxito");
            } else {
                // Caso extremo: existe publicación pero no es servicio ni producto
                return ResponseEntity.internalServerError()
                        .body("Error: La publicación existe pero no se encontró ni en servicios ni productos.");
            }
        }
    }


    public ResponseEntity<?> calificarPublicacion(Long idUsuario, Long idPublicacion, CalificacionDTO dto) {
        try {
            Calificacion calificacion = new Calificacion(dto.getCalificacion(), bdd_usuarios.findById(idUsuario).get(), bdd_publicaciones.findById(idPublicacion).get());
            bdd_calificacion.save(calificacion);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("Calificación creada correctamente");
    }

    public List<Calificacion> verCalificaciones(Long id) {
        return bdd_calificacion.findByPublicacion(bdd_publicaciones.findById(id).get());
    }

    public ResponseEntity<?> reportarPublicacion(Long idUsuario, Long idPublicacion, ReporteDTO dto) {
        try {
            Reporte reporte = new Reporte(dto.getMensaje(), bdd_usuarios.findById(idUsuario).get(), bdd_publicaciones.findById(idPublicacion).get());
            bdd_reportes.save(reporte);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("Reporte realizado con éxito");
    }

    public List<ReportesDTO> verPublicacionesReportadas(){
        List<ReportesDTO> reportes = new ArrayList<>();
        List<Publicacion> publicaciones = bdd_publicaciones.findAll();
        for (Publicacion p : publicaciones){
            if (!bdd_reportes.findByPublicacion(p).isEmpty()){
                ReportesDTO reporte = new ReportesDTO(p, bdd_reportes.findByPublicacion(p));
                reportes.add(reporte);
            }
        }
        return reportes;
    }

    public List<MostrarPublicacionDTO> getAll(){
        List<Publicacion> publis =  bdd_publicaciones.findAll();
        List<MostrarPublicacionDTO> dtos = new ArrayList<>();
        for (Publicacion p : publis){
            if (!bdd_productos.findById(p.getId()).isEmpty()){
                MostrarPublicacionDTO dto = new MostrarPublicacionDTO(p.getNombre(), p.getPrecio(), "PRODUCTO");
                System.out.println(dto.getCategoria());
                dtos.add(dto);
            } else {
                MostrarPublicacionDTO dto = new MostrarPublicacionDTO(p.getNombre(), p.getPrecio(), "SERVICIO");
                System.out.println(dto.getCategoria());
                dtos.add(dto);
            }
        }
        return dtos;
    }

}
