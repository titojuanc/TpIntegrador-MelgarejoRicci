package com.mercadotecnico.mr.mercadotecnico.repository;

import com.mercadotecnico.mr.mercadotecnico.model.ServicioDiaId;
import com.mercadotecnico.mr.mercadotecnico.model.Servicio_has_dia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiasServicioRepository extends JpaRepository<Servicio_has_dia, ServicioDiaId> {
    Optional<Servicio_has_dia> findById(ServicioDiaId id_servicio);

}
