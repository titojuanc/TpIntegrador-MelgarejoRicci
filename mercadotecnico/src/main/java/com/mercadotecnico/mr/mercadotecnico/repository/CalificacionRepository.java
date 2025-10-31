package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Calificacion;
import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    public Optional<Calificacion> findById(Long id);
    public List<Calificacion> findByPublicacion (Publicacion publicacion);
}
