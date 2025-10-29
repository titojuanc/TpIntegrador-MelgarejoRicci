package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.Notificacion;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificiacionRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findByUsuario(Usuario u);


}
