package com.mercadotecnico.mr.mercadotecnico.dto;

public class ReporteDTO {
    private String mensaje;

    public ReporteDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
