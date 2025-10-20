package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    //Busca por id
    Optional<Publicacion> findById(Long id);

    //Busca por idUsuario
    Optional<List<Publicacion>> findByUsuario_Id(Long idUsuario);
}
