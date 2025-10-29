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
    private int stock;

    public Producto(){}

    public Producto(Publicacion publicacion, int garantia, int stock){
        this.publicacion=publicacion;
        this.garantia=garantia;
        this.stock=stock;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
