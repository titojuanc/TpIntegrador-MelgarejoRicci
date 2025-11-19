package com.mercadotecnico.mr.mercadotecnico.dto;

public class MostrarCalificacionesDTO {
    private String nombre_usuario;
    private double puntuacion;
    private String nombre_publicacion;

    public MostrarCalificacionesDTO(String nombre_usuario, double puntuacion, String nombre_publicacion) {
        this.nombre_usuario = nombre_usuario;
        this.puntuacion = puntuacion;
        this.nombre_publicacion = nombre_publicacion;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNombre_publicacion() {
        return nombre_publicacion;
    }

    public void setNombre_publicacion(String nombre_publicacion) {
        this.nombre_publicacion = nombre_publicacion;
    }
}
