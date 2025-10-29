package com.mercadotecnico.mr.mercadotecnico.service;

import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.repository.PublicacionRepository;
import org.springframework.stereotype.Service;

@Service
public class PublicacionService {
    private PublicacionRepository bdd_publicaciones;

    public PublicacionService(PublicacionRepository bdd_publicaciones) {
        this.bdd_publicaciones = bdd_publicaciones;
    }

    public Publicacion actualizarEstado(Long id, String estado) throws Exception {
        Publicacion actualizar = bdd_publicaciones.findById(id).get();

        if (actualizar == null) {
            throw new Exception("No se encontró la especialidad con ID " + id);
        }

        actualizar.setEstado(estado);
        return bdd_publicaciones.save(actualizar);
    }
}
