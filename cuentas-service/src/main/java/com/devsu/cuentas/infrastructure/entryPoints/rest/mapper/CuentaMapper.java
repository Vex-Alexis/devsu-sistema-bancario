package com.devsu.cuentas.infrastructure.entryPoints.rest.mapper;

import com.devsu.cuentas.domain.model.cuenta.Cuenta;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.CuentaRequest;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.CuentaUpdateRequest;

public class CuentaMapper {
    public static Cuenta toDomain(CuentaRequest request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(request.getNumeroCuenta());
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setEstado(request.getEstado());
        cuenta.setClienteId(request.getClienteId());
        cuenta.setSaldoInicial(0.0); // puedes cambiar esto si viene en el request
        return cuenta;
    }

    public static Cuenta toDomain(CuentaUpdateRequest request) {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(request.getTipoCuenta());
        cuenta.setEstado(request.getEstado());
        return cuenta;
    }

}
