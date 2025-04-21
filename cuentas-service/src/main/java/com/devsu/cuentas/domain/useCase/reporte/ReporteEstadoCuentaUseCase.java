package com.devsu.cuentas.domain.useCase.reporte;

import com.devsu.cuentas.domain.model.reporte.EstadoDeCuenta;

import java.time.LocalDate;

public interface ReporteEstadoCuentaUseCase {
    EstadoDeCuenta generarEstadoDeCuenta(String identificacionCliente, LocalDate desde, LocalDate hasta);

}
