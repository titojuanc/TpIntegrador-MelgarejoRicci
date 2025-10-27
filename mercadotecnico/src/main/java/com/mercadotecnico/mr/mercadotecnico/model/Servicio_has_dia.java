package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;


@Entity
@Table(name = "servicio_has_dia", schema = "TPMercadoTecnico")
public class Servicio_has_dia {

    @EmbeddedId
    private ServicioDiaId id;

    @ManyToOne
    @MapsId("id_servicio")
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;


    @ManyToOne
    @MapsId("id_dia")
    @JoinColumn(name = "id_dia", nullable = false)
    private Dia dia;

    public Servicio_has_dia(){}

    public Servicio_has_dia(Servicio servicio, Dia dia) {
        this.servicio = servicio;
        this.dia = dia;
        if (servicio != null && servicio.getId() != null && dia != null && dia.getId() != null) {
            this.id = new ServicioDiaId(servicio.getId(), dia.getId());
        }
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
