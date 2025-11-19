package com.mercadotecnico.mr.mercadotecnico.controller;

import com.mercadotecnico.mr.mercadotecnico.dto.NivelDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.InfoPerfilDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.ReputacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.Calificacion;
import com.mercadotecnico.mr.mercadotecnico.model.Nivel;
import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import com.mercadotecnico.mr.mercadotecnico.repository.CalificacionRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.NivelRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.PublicacionRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import com.mercadotecnico.mr.mercadotecnico.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class    UsuarioController {

    private UsuarioService servicio_usuario;

    public UsuarioController(UsuarioService servicio_usuario) {
        this.servicio_usuario = servicio_usuario;
    }

    // Punto 3a
    @PostMapping("/POST/api/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return servicio_usuario.crearUsuario(usuario);
    }

    @GetMapping("/GET/api/usuarios/{idUsuario}/publicaciones")
    public Optional<List<Publicacion>> obtenerPublicacionesDeUsuario(@PathVariable Long idUsuario){
        return servicio_usuario.obtenerPublicacionesDeUsuario(idUsuario);
    }

    @GetMapping("GET/api/usuarios/{id}/nivel")
    public NivelDTO verNivel(@PathVariable Long id){
        return servicio_usuario.verNivel(id);
    }

    @GetMapping("/GET/api/usuarios/{id}/reputacion")
    public ReputacionDTO verReputacion(@PathVariable Long id){
        return servicio_usuario.verReputacion(id);
    }

    @GetMapping("GET/api/usuarios/{id}")
    public InfoPerfilDTO infoUsuario(@PathVariable Long id){
        return servicio_usuario.infoUsuario(id);
    }
}
