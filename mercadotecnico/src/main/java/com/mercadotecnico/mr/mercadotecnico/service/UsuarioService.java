package com.mercadotecnico.mr.mercadotecnico.service;

import com.mercadotecnico.mr.mercadotecnico.controller.UsuarioController;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    UserRepository bdd_usuarios;
    PublicacionRepository bdd_publicaciones;
    NivelRepository bdd_nivel;
    CalificacionRepository bdd_calificaciones;

    public UsuarioService(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, NivelRepository bdd_nivel, CalificacionRepository bdd_calificaciones) {
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_nivel = bdd_nivel;
        this.bdd_calificaciones = bdd_calificaciones;
    }

    public Optional<Usuario> obtenerPorNombre(String nombre){
        return bdd_usuarios.findByNombre(nombre);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return bdd_usuarios.save(usuario);
    }

    public Optional<List<Publicacion>> obtenerPublicacionesDeUsuario(Long idUsuario){
        return bdd_publicaciones.findByUsuario_Id(idUsuario);
    }

    public NivelDTO verNivel(Long id){
        Usuario usuario = bdd_usuarios.findById(id).get();
        Long idNivel = (long) usuario.getId_nivel();
        Nivel nivel = bdd_nivel.findById(idNivel).get();
        NivelDTO nivelYdescuento = new NivelDTO(usuario, nivel.getNombre(), nivel.getDescuento());
        return nivelYdescuento;
    }

    public ReputacionDTO verReputacion(Long id){
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

    public InfoPerfilDTO infoUsuario(Long id){
        Usuario usuario = bdd_usuarios.findById(id).get();
        String nivel = bdd_nivel.findById(usuario.getId_nivel()).get().getNombre();
        int puntos = usuario.getPuntos_acumulados();
        int cantidad_publicacion = obtenerPublicacionesDeUsuario(id).get().size();

        InfoPerfilDTO dto = new InfoPerfilDTO(usuario.getNombre(), nivel ,cantidad_publicacion, puntos);
        return dto;
    }


}
