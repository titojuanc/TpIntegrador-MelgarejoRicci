package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Compra;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    @Override
    Optional<Compra> findById(Long id);
    List<Compra> findByUsuario(Usuario usuario);
}
