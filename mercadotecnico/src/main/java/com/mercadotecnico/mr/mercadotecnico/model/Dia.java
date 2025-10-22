package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;

public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia;

    public Dia(){}

    public Dia(Long id, String dia) {
        this.id = id;
        this.dia = dia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
