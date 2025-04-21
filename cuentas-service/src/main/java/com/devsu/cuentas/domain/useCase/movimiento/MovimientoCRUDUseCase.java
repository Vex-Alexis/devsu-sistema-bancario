package com.devsu.cuentas.domain.useCase.movimiento;

import com.devsu.cuentas.domain.model.movimiento.Movimiento;

import java.util.List;
import java.util.Optional;

public interface MovimientoCRUDUseCase {
    Movimiento crearMovimiento(Movimiento movimiento);
    List<Movimiento> obtenerTodosLosMovimientos();
    Optional<Movimiento> obtenerMovimientoPorId(Long movimientoId);
    List<Movimiento> obtenerMovimientosPorCuentaId(Long cuentaId);
    Movimiento revertirMovimiento(Long movimientoId);
}
