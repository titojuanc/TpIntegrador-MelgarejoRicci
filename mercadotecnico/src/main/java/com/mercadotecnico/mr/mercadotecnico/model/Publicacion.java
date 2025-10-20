package com.mercadotecnico.mr.mercadotecnico.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Publicacion {
    /* id int primary key auto_increment,
        nombre varchar(255) not null,
        descripcion varchar(255) not null,
        fecha_publicacion date not null,
        precio decimal(10,2) not null,
        stock int not null,
        id_usuario int,
        estado varchar(255) not null,
        foreign key (id_usuario) references usuario(id)*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaPublicacion;
    private Double precio;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // <-- relación con la entidad Usuario

    private String estado;

    public Publicacion() {}

    public Publicacion(Long id, String nombre, String descripcion, LocalDate fechaPublicacion,
                       Double precio, int stock, Usuario usuario, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.stock = stock;
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

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}