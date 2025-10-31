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

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    private boolean usado;

    public Producto(){}

    public Producto(Publicacion publicacion, int garantia, int stock, Categoria categoria, boolean usado) {
        this.publicacion = publicacion;
        this.garantia = garantia;
        this.stock = stock;
        this.categoria = categoria;
        this.usado = usado;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}
