package com.devsu.cuentas.infrastructure.entryPoints.rest.mapper;

import com.devsu.cuentas.domain.model.movimiento.Movimiento;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.MovimientoRequest;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.MovimientoUpdateRequest;

import java.time.LocalDate;

public class MovimientoMapper {
    public static Movimiento toDomain(MovimientoRequest movimientoRequest) {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(movimientoRequest.getTipoMovimiento());
        movimiento.setValor(movimientoRequest.getValor());
        movimiento.setCuentaId(movimientoRequest.getCuentaId());
        return movimiento;
    }

    public static Movimiento toDomain(MovimientoUpdateRequest movimientoUpdateRequest) {
        Movimiento movimiento = new Movimiento();
        movimiento.setTipoMovimiento(movimientoUpdateRequest.getTipoMovimiento());
        movimiento.setValor(movimientoUpdateRequest.getValor());
        return movimiento;
    }
}
