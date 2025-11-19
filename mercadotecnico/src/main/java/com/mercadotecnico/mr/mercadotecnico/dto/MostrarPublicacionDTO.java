package com.mercadotecnico.mr.mercadotecnico.dto;

public class MostrarPublicacionDTO {
    private String nombre;
    private double precio;
    private String categoría;

    public MostrarPublicacionDTO(String nombre, double precio, String categoría) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoría = categoría;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoría() {
        return categoría;
    }

    public void setCategoría(String categoría) {
        this.categoría = categoría;
    }
}
