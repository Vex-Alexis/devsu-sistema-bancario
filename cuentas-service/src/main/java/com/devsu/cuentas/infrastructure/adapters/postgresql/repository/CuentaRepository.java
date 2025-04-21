package com.devsu.cuentas.infrastructure.adapters.postgresql.repository;

import com.devsu.cuentas.infrastructure.adapters.postgresql.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
    List<CuentaEntity> findByClienteId(Long clienteId);
    Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
}
