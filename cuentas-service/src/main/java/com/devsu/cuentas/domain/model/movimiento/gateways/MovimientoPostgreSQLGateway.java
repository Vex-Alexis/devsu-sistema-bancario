package com.devsu.cuentas.domain.model.movimiento.gateways;

import com.devsu.cuentas.domain.model.movimiento.Movimiento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovimientoPostgreSQLGateway {
    Movimiento crearMovimiento(Movimiento movimiento);
    List<Movimiento> obtenerTodosLosMovimientos();
    Optional<Movimiento> obtenerMovimientoPorId(Long movimientoId);
    List<Movimiento> obtenerMovimientosPorCuentaId(Long cuentaId);
    List<Movimiento> obtenerMovimientosPorCuentaYFecha(Long cuentaId, LocalDate desde, LocalDate hasta);
    Movimiento actualizarMovimiento(Long movimientoId, Movimiento movimiento);
}
