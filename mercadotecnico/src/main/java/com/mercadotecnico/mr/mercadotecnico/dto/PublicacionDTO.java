package com.mercadotecnico.mr.mercadotecnico.dto;

import com.mercadotecnico.mr.mercadotecnico.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class PublicacionDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fechaPublicacion;
    private Double precio;
    private int stock;
    private String estado;
    private String tipo;
    private boolean usado;
    private String frecuencia;
    private int categoria;
    private int garantia;
    private List<Long> dias;

    public PublicacionDTO(String nombre, String descripcion, LocalDate fechaPublicacion, Double precio, int stock, String estado, String tipo, boolean usado, String frecuencia, int categoria, int garantia, List<Long> dias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.tipo = tipo;
        this.usado = usado;
        this.frecuencia = frecuencia;
        this.categoria = categoria;
        this.garantia = garantia;
        this.dias = dias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    public List<Long> getDias() {
        return dias;
    }

    public void setDias(List<Long> dias) {
        this.dias = dias;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
}
