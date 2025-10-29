package com.mercadotecnico.mr.mercadotecnico.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CompraDTO {
    private double total;
    private Long id_usuario;
    private Long id_publicacion;
    private int cantidad;
    List<Long> dias_elegidos;
    List<LocalDateTime> fechas_elegidas;

    public CompraDTO(List<LocalDateTime> fechasElegidas ,double total, Long id_usuario, Long id_publicacion, int cantidad, List<Long> dias_elegidos) {
        this.total = total;
        this.id_usuario = id_usuario;
        this.id_publicacion = id_publicacion;
        this.cantidad = cantidad;
        this.dias_elegidos = dias_elegidos;
        this.fechas_elegidas=fechasElegidas;

    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(Long id_publicacion) {
        this.id_publicacion = id_publicacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public List<Long> getDias_elegidos() {
        return dias_elegidos;
    }

    public void setDias_elegidos(List<Long> dias_elegidos) {
        this.dias_elegidos = dias_elegidos;
    }

    public List<LocalDateTime> getFechas_elegidas() {
        return fechas_elegidas;
    }

    public void setFechas_elegidas(List<LocalDateTime> fechas_elegidas) {
        this.fechas_elegidas = fechas_elegidas;
    }
}
