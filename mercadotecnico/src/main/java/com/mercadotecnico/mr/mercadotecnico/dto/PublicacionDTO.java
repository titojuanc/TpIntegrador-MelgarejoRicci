package com.mercadotecnico.mr.mercadotecnico.dto;

import com.mercadotecnico.mr.mercadotecnico.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class PublicacionDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaPublicacion;
    private Double precio;
    private int stock;
    private int id_usuario;
    private String estado;
    private String tipo;
    private int garantia;
    private List<Integer> dias;
}
