package com.mercadotecnico.mr.mercadotecnico.model;


import jakarta.persistence.*;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String mensaje;
   @ManyToOne
   @JoinColumn(name = "id_usuario", nullable = false)
   private Usuario usuario;

    public Notificacion(Long id, String mensaje, Usuario usuario) {
        this.id = id;
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    public Notificacion() {
    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
