package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;


@Entity
@Table(name = "producto")
public class Producto {

    @Id
    private Long id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    private int garantia;

    public Producto(){}

    public Producto(Publicacion publicacion, int garantia){
        this.publicacion=publicacion;
        this.garantia=garantia;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }
}
