package com.mercadotecnico.mr.mercadotecnico.dto;

import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.model.Reporte;

import java.util.List;

public class ReportesDTO {
    private Publicacion publicacion;
    private List<Reporte> reportes;

    public ReportesDTO(Publicacion publicacion, List<Reporte> reportes) {
        this.publicacion = publicacion;
        this.reportes = reportes;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public List<Reporte> getReportes() {
        return reportes;
    }

    public void setReportes(List<Reporte> reportes) {
        this.reportes = reportes;
    }
}
