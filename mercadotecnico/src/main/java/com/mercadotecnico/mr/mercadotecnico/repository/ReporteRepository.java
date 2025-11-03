package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Override
    Optional<Reporte> findById(Long id);
    List<Reporte> findByPublicacion(Publicacion publicacion);
}
