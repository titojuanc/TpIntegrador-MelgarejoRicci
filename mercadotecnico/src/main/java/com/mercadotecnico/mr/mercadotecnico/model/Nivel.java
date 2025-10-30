package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;

@Entity
@Table(name = "nivel")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private double descuento;

    public Nivel(Long id, String nombre, double descuento) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public Nivel(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
