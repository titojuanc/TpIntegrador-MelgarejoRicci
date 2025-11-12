package com.mercadotecnico.mr.mercadotecnico.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CompraDTO {
    private double total;
    private int cantidad;
    List<Long> dias_elegidos;
    List<LocalDateTime> fechas_elegidas;

    public CompraDTO(List<LocalDateTime> fechasElegidas ,double total, int cantidad, List<Long> dias_elegidos) {
        this.total = total;
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
