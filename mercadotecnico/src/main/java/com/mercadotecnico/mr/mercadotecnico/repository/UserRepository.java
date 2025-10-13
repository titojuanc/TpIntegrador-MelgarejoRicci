package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {

    //Busca por nombre exacto
    Optional<Usuario> findByNombre(String nombre);

    //Verificar si existe un usuario por nombre
    boolean existsByNombre(String nombre);

    //Usuario buscarPorNombre(String nombre);
}
