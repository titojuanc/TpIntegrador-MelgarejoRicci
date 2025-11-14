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

    PublicacionService servicio_publicaciones;

    public PublicacionController( PublicacionService servicio_publicaciones){
        this.servicio_publicaciones = servicio_publicaciones;
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicaciones")
    public void crearPublicacion(@PathVariable Long idUsuario, @RequestBody PublicacionDTO dto){
        servicio_publicaciones.crearPublicacion(idUsuario, dto);
    }

    @GetMapping("/GET/api/publicacion/{id}")
    public Optional<Publicacion> obtenerPorId(@PathVariable Long id){
        return servicio_publicaciones.obtenerPorId(id);
    }

    @DeleteMapping("DELETE/api/admin/publicaciones/{id}")
    public ResponseEntity<?> eliminarPublicacion(@PathVariable Long id) throws Exception {
        return servicio_publicaciones.eliminarPublicacion(id);
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicacion/{idPublicacion}/calificar")
    public ResponseEntity<?> calificarPublicacion(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody CalificacionDTO dto){
        return servicio_publicaciones.calificarPublicacion(idUsuario, idPublicacion, dto);
    }

    @GetMapping("/GET/api/publicacion/{id}/calificaciones")
    public List<Calificacion> verCalificaciones(@PathVariable Long id) {
        return servicio_publicaciones.verCalificaciones(id);
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicacion/{idPublicacion}/reportar")
    public ResponseEntity<?> reportarPublicacion(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody ReporteDTO dto){
        return servicio_publicaciones.reportarPublicacion(idUsuario, idPublicacion, dto);
    }

    @GetMapping("/GET/api/admin/reportes")
    public List<ReportesDTO> verPublicacionesReportadas(){
        return servicio_publicaciones.verPublicacionesReportadas();
    }

    @GetMapping("/GET/api/publicaciones")
    public List<Publicacion> filtrarPublicaciones(
            @RequestParam(required = false) boolean usado,
            @RequestParam(required = false) int categoria,
            @RequestParam(required = false) String tipo){
        return servicio_publicaciones.filtrar(usado, categoria, tipo);
    }
}
