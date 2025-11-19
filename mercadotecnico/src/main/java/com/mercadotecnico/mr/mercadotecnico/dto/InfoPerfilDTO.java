package com.mercadotecnico.mr.mercadotecnico.dto;

public class InfoPerfilDTO {
    private String nombre;
    private String nivel;
    private int cantidad_publicaciones;
    private int puntos;

    public InfoPerfilDTO(String nombre, String nivel, int cantidad_publicaciones, int puntos) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.cantidad_publicaciones = cantidad_publicaciones;
        this.puntos = puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad_publicaciones() {
        return cantidad_publicaciones;
    }

    public void setCantidad_publicaciones(int cantidad_publicaciones) {
        this.cantidad_publicaciones = cantidad_publicaciones;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
