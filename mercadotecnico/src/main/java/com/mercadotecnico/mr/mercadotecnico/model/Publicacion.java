package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table (name = "publicacion")
public class Publicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaPublicacion;
    private Double precio;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // <-- relación con la entidad Usuario

    public Publicacion() {}

    public Publicacion(String nombre, String descripcion, LocalDate fechaPublicacion,
                       Double precio, Usuario usuario, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.usuario = usuario;
        this.estado = estado;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}