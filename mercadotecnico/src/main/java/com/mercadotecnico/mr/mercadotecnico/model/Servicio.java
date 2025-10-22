package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Servicio {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion; // <-- relación con la entidad Usuario

    public Servicio(){}

    public Servicio(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
