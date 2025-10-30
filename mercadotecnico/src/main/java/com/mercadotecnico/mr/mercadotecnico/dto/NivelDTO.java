package com.mercadotecnico.mr.mercadotecnico.dto;

import com.mercadotecnico.mr.mercadotecnico.model.Usuario;

public class NivelDTO {
    private Usuario usuario;
    private String nombreNivel;
    private double descuento;

    public NivelDTO(Usuario usuario,String nombreNivel, double descuento) {
        this.usuario = usuario;
        this.descuento = descuento;
        this.nombreNivel= nombreNivel;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public void setNombreNivel(String nombreNivel) {
        this.nombreNivel = nombreNivel;
    }
}
