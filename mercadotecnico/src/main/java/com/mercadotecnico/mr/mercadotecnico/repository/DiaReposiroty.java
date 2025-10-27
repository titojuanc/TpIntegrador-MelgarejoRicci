package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Dia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaReposiroty extends JpaRepository<Dia, Long> {
    @Override
    Optional<Dia> findById(Long id);
}
