package com.mercadotecnico.mr.mercadotecnico.model;

import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import jakarta.persistence.*;

@Entity
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private int puntos_acumulados;
    private boolean es_admin;
    private int id_nivel;

    public Usuario(){}

    public Usuario(String nombre, String apellido, String mail, String contrasenia, int puntos_acumulados, boolean esAdmin, int id_nivel) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = mail;
        this.contrasena = contrasenia;
        this.puntos_acumulados = puntos_acumulados;
        this.es_admin = esAdmin;
        this.id_nivel = id_nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasenia) {
        this.contrasena = contrasenia;
    }

    public int getPuntos_acumulados() {
        return puntos_acumulados;
    }

    public void setPuntos_acumulados(int puntos_acumulados) {
        this.puntos_acumulados = puntos_acumulados;
    }

    public boolean isEsAdmin() {
        return es_admin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.es_admin = esAdmin;
    }

    public int getId_nivel() {
        return id_nivel;
    }

    public void setId_nivel(int id_nivel) {
        this.id_nivel = id_nivel;
    }
}
