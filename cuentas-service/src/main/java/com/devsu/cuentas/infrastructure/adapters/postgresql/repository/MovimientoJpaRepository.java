package com.devsu.cuentas.infrastructure.adapters.postgresql.repository;

import com.devsu.cuentas.infrastructure.adapters.postgresql.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimientoJpaRepository extends JpaRepository<MovimientoEntity, Long> {
    List<MovimientoEntity> findByCuentaId(Long cuentaId);
    List<MovimientoEntity> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate desde, LocalDate hasta);

}
