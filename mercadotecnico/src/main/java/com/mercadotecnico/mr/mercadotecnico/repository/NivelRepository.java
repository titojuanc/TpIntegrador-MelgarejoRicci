package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NivelRepository extends JpaRepository<Nivel, Long> {
    Optional<Nivel> findById(Long id);
}
