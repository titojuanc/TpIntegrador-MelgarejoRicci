package com.mercadotecnico.mr.mercadotecnico.controller;

import com.mercadotecnico.mr.mercadotecnico.dto.CalificacionDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.PublicacionDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.ReporteDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.ReportesDTO;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
import com.mercadotecnico.mr.mercadotecnico.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PublicacionController {

    UserRepository bdd_usuarios;
    PublicacionRepository bdd_publicaciones;
    DiaReposiroty bdd_dias;
    ServicioRepository bdd_servicios;
    ProductoRepository bdd_productos;
    DiasServicioRepository bdd_diasDeServicio;
    CategoriaRepository bdd_categoria;
    CalendarioRepository bdd_calendario;
    PublicacionService servicio_publicaciones;
    CalificacionRepository bdd_calificacion;
    ReporteRepository bdd_reportes;

    public PublicacionController(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, DiaReposiroty bdd_dias, ServicioRepository bdd_servicios, ProductoRepository bdd_productos, DiasServicioRepository bdd_diasDeServicio, CategoriaRepository bdd_categoria, CalendarioRepository bdd_calendario, PublicacionService servicio_publicaciones, CalificacionRepository bdd_calificacion, ReporteRepository bdd_reportes) {
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_dias = bdd_dias;
        this.bdd_servicios = bdd_servicios;
        this.bdd_productos = bdd_productos;
        this.bdd_diasDeServicio = bdd_diasDeServicio;
        this.bdd_categoria = bdd_categoria;
        this.bdd_calendario = bdd_calendario;
        this.servicio_publicaciones = servicio_publicaciones;
        this.bdd_calificacion = bdd_calificacion;
        this.bdd_reportes = bdd_reportes;
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicaciones")
    public void crearPublicacion(@PathVariable Long idUsuario, @RequestBody PublicacionDTO dto){
        servicio_publicaciones.crearPublicacion(idUsuario, dto);
    }

    @GetMapping("/GET/api/publicacion/{id}")
    public Optional<Publicacion> obtenerPorId(@PathVariable Long id){
        return bdd_publicaciones.findById(id);
    }

    @DeleteMapping("DELETE/api/admin/publicaciones/{id}")
    public ResponseEntity<?> eliminarPublicacion(@PathVariable Long id) throws Exception {
        if (bdd_publicaciones.findById(id).isPresent()){
            Publicacion publicacion = bdd_publicaciones.findById(id).get();
            if (bdd_servicios.findById(id).isPresent()){
                if (!bdd_calendario.findByServicio(bdd_servicios.findById(id).get()).isEmpty()){
                    servicio_publicaciones.actualizarEstado(id, "En pausa");
                    return ResponseEntity.ok("La publicación se pausó porque hay usuarios que tienen contratado el servicio. Una vez finalizado, puede borrarse");
                } else {
                    bdd_servicios.delete(bdd_servicios.findById(id).get());
                    bdd_publicaciones.delete(publicacion);
                    return ResponseEntity.ok("Se borró con éxito");
                }
            } else { // Si no es servicio, es producto si o si
                bdd_productos.delete(bdd_productos.findById(id).get());
                bdd_publicaciones.delete(publicacion);
                return ResponseEntity.ok("Se borró con éxito");
            }
        } else {
            return ResponseEntity.badRequest().body("No existe publicacion con ese ID");
        }
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicacion/{idPublicacion}/calificar")
    public ResponseEntity<?> calificarPublicacion(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody CalificacionDTO dto){
        try{
            Calificacion calificacion = new Calificacion(dto.getCalificacion(), bdd_usuarios.findById(idUsuario).get(), bdd_publicaciones.findById(idPublicacion).get());
            bdd_calificacion.save(calificacion);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("Calificación creada correctamente");
    }

    @GetMapping("/GET/api/publicacion/{id}/calificaciones")
    public List<Calificacion> verCalificaciones(@PathVariable Long id){
        return bdd_calificacion.findByPublicacion(bdd_publicaciones.findById(id).get());
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicacion/{idPublicacion}/reportar")
    public ResponseEntity<?> reportarPublicacion(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody ReporteDTO dto){
        try {
            Reporte reporte = new Reporte(dto.getMensaje(), bdd_usuarios.findById(idUsuario).get(), bdd_publicaciones.findById(idPublicacion).get());
            bdd_reportes.save(reporte);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok("Reporte realizado con éxito");
    }

    @GetMapping("/GET/api/admin/reportes")
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

    @GetMapping("/GET/api/publicaciones")
    public List<Publicacion> filtrarPublicaciones(
            @RequestParam(required = false) boolean usado,
            @RequestParam(required = false) int categoria,
            @RequestParam(required = false) String tipo){
        return servicio_publicaciones.filtrar(usado, categoria, tipo);
    }
}
