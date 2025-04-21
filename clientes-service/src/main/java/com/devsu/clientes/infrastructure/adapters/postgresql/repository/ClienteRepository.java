package com.devsu.clientes.infrastructure.adapters.postgresql.repository;

import com.devsu.clientes.infrastructure.adapters.postgresql.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByIdentificacion(String identificacion);
}
