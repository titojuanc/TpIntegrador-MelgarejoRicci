package com.mercadotecnico.mr.mercadotecnico.dto;

import com.mercadotecnico.mr.mercadotecnico.model.Calificacion;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;

import java.util.List;

public class ReputacionDTO {
    private Usuario usuario;
    private List<Calificacion> calificaciones;


    public ReputacionDTO(Usuario usuario, List<Calificacion> calificaciones) {
        this.usuario = usuario;
        this.calificaciones = calificaciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
