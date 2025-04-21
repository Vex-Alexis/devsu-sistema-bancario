package com.devsu.cuentas.infrastructure.adapters.postgresql.adapter;

import com.devsu.cuentas.domain.model.movimiento.Movimiento;
import com.devsu.cuentas.domain.model.movimiento.gateways.MovimientoPostgreSQLGateway;
import com.devsu.cuentas.infrastructure.adapters.postgresql.entity.MovimientoEntity;
import com.devsu.cuentas.infrastructure.adapters.postgresql.repository.MovimientoJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovimientoAdapter implements MovimientoPostgreSQLGateway {


    private final MovimientoJpaRepository movimientoJpaRepository;

    public MovimientoAdapter(MovimientoJpaRepository movimientoJpaRepository) {
        this.movimientoJpaRepository = movimientoJpaRepository;
    }

    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        MovimientoEntity entity = toEntity(movimiento);
        MovimientoEntity saved = movimientoJpaRepository.save(entity);
        return toModel(saved);
    }

    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoJpaRepository.findAll()
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Movimiento> obtenerMovimientoPorId(Long movimientoId) {
        return movimientoJpaRepository.findById(movimientoId)
                .map(this::toModel);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaId(Long cuentaId) {
        return movimientoJpaRepository.findByCuentaId(cuentaId)
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaYFecha(Long cuentaId, LocalDate desde, LocalDate hasta) {
        return movimientoJpaRepository
                .findByCuentaIdAndFechaBetween(cuentaId, desde, hasta)
                .stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Movimiento actualizarMovimiento(Long movimientoId, Movimiento movimiento) {
        MovimientoEntity entity = movimientoJpaRepository.findById(movimientoId)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));

        // Actualizamos campos permitidos
        entity.setTipoMovimiento(movimiento.getTipoMovimiento());
        entity.setValor(movimiento.getValor());

        MovimientoEntity actualizado = movimientoJpaRepository.save(entity);
        return toModel(actualizado);
    }


    // ========== MÉTODOS DE MAPEOS ==========

    private MovimientoEntity toEntity(Movimiento movimiento) {
        MovimientoEntity entity = new MovimientoEntity();
        entity.setMovimientoId(movimiento.getMovimientoId());
        entity.setFecha(movimiento.getFecha());
        entity.setTipoMovimiento(movimiento.getTipoMovimiento());
        entity.setValor(movimiento.getValor());
        entity.setSaldo(movimiento.getSaldo());
        entity.setCuentaId(movimiento.getCuentaId());
        return entity;
    }

    private Movimiento toModel(MovimientoEntity entity) {
        Movimiento movimiento = new Movimiento();
        movimiento.setMovimientoId(entity.getMovimientoId());
        movimiento.setFecha(entity.getFecha());
        movimiento.setTipoMovimiento(entity.getTipoMovimiento());
        movimiento.setValor(entity.getValor());
        movimiento.setSaldo(entity.getSaldo());
        movimiento.setCuentaId(entity.getCuentaId());
        return movimiento;
    }
}
