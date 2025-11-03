package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;

import java.lang.reflect.Array;


@Entity
@Table(name = "servicio")
public class Servicio {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    private String frecuencia;

    public Servicio(){}

    public Servicio(Publicacion publicacion, String frecuencia) {
        this.publicacion = publicacion;
        this.frecuencia = frecuencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
}
