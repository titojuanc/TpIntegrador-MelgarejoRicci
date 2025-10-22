package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

public class Servicio_has_dia {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_dia", nullable = false)
    private Dia dia;

    public Servicio_has_dia(){}

    public Servicio_has_dia(Servicio servicio, Dia dia) {
        this.servicio = servicio;
        this.dia = dia;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }
}
