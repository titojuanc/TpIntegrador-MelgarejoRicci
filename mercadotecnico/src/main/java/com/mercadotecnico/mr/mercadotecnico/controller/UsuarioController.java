package com.mercadotecnico.mr.mercadotecnico.controller;

import com.mercadotecnico.mr.mercadotecnico.dto.NivelDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.ReputacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.Calificacion;
import com.mercadotecnico.mr.mercadotecnico.model.Nivel;
import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import com.mercadotecnico.mr.mercadotecnico.repository.CalificacionRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.NivelRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.PublicacionRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UsuarioController {

    UserRepository bdd_usuarios;
    PublicacionRepository bdd_publicaciones;
    NivelRepository bdd_nivel;
    CalificacionRepository bdd_calificaciones;

    public UsuarioController(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, NivelRepository bdd_nivel, CalificacionRepository bdd_calificaciones) {
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_nivel = bdd_nivel;
        this.bdd_calificaciones = bdd_calificaciones;
    }

    @GetMapping("/GET/api/usuarios/{nombre}")
    public Optional<Usuario> obtenerPorNombre(@PathVariable String nombre) {
        System.out.println(nombre);
        return bdd_usuarios.findByNombre(nombre);
    }

    // Punto 3a
    @PostMapping("/POST/api/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        System.out.println(usuario);
        return bdd_usuarios.save(usuario);
    }

    @GetMapping("/GET/api/usuarios/{idUsuario}/publicaciones")
    public Optional<List<Publicacion>> obtenerPublicacionesDeUsuario(@PathVariable Long idUsuario){
        return bdd_publicaciones.findByUsuario_Id(idUsuario);
    }

    @GetMapping("GET/api/usuarios/{id}/nivel")
    public NivelDTO verNivel(@PathVariable Long id){
        Usuario usuario = bdd_usuarios.findById(id).get();
        Long idNivel = (long) usuario.getId_nivel();
        Nivel nivel = bdd_nivel.findById(idNivel).get();
        NivelDTO nivelYdescuento = new NivelDTO(usuario, nivel.getNombre(), nivel.getDescuento());
        return nivelYdescuento;
    }

    @GetMapping("/GET/api/usuarios/{id}/reputacion")
    public ReputacionDTO verReputacion(@PathVariable Long id){
        Usuario usuario = bdd_usuarios.findById(id).get();
        List<Publicacion> publicaciones = bdd_publicaciones.findByUsuario_Id(id).get();
        List<Calificacion> calificaciones = new ArrayList<>();
        for (Publicacion p : publicaciones){
            List<Calificacion> calificacionAux = bdd_calificaciones.findByPublicacion(p);
            calificaciones.addAll(calificacionAux);
        }

        ReputacionDTO dto = new ReputacionDTO(usuario, calificaciones);
        return dto;
    }
}
