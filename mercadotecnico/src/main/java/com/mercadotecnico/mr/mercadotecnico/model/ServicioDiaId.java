package com.mercadotecnico.mr.mercadotecnico.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServicioDiaId implements Serializable {
    @Column(name = "id_servicio")
    private Long id_servicio;

    @Column(name = "id_dia")
    private Long id_dia;

    public ServicioDiaId(){}

    public ServicioDiaId(Long id_servicio, Long id_dia) {
        this.id_servicio = id_servicio;
        this.id_dia = id_dia;
    }

    public Long getid_servicio() {
        return id_servicio;
    }

    public void setid_servicio(Long id_servicio) {
        this.id_servicio = id_servicio;
    }

    public Long getid_dia() {
        return id_dia;
    }

    public void setid_dia(Long id_dia) {
        this.id_dia = id_dia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServicioDiaId)) return false;
        ServicioDiaId that = (ServicioDiaId) o;
        return Objects.equals(id_servicio, that.id_servicio) &&
                Objects.equals(id_dia, that.id_dia);
    }
}
