package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Calendario;
import com.mercadotecnico.mr.mercadotecnico.model.Servicio;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    @Override
    Optional<Calendario> findById(Long id);
    Optional<Calendario> findByUsuario(Usuario usuario);
    Optional<Calendario> findByServicio (Servicio servicio);
}
