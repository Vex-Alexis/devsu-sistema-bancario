package com.devsu.cuentas.application.useCase.cuenta;

import com.devsu.cuentas.domain.model.cuenta.Cuenta;
import com.devsu.cuentas.domain.model.cuenta.gateways.CuentaPostgreSQLGateway;
import com.devsu.cuentas.domain.model.exceptions.DuplicateAccountException;
import com.devsu.cuentas.domain.model.exceptions.NotFoundException;
import com.devsu.cuentas.domain.useCase.cuenta.CuentaCRUDUseCase;
import com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request.CuentaUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CuentaUseCase implements CuentaCRUDUseCase {
    private final CuentaPostgreSQLGateway cuentaPostgreSQLGateway;

    public CuentaUseCase(CuentaPostgreSQLGateway cuentaPostgreSQLGateway) {
        this.cuentaPostgreSQLGateway = cuentaPostgreSQLGateway;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        Optional<Cuenta> cuentaExistente = cuentaPostgreSQLGateway.obtenerCuentaPorNumeroCuenta(cuenta.getNumeroCuenta());
        if(cuentaExistente.isPresent()){
            throw new DuplicateAccountException("Ya existe una cuenta bancaria con el número de cuenta: " + cuenta.getNumeroCuenta());
        }
        return cuentaPostgreSQLGateway.crearCuenta(cuenta);
    }

    @Override
    public Cuenta consultarCuentaPorNumeroCuenta(String numeroCuenta) {
        return cuentaPostgreSQLGateway.obtenerCuentaPorNumeroCuenta(numeroCuenta)
                .orElseThrow(()-> new NotFoundException("La cuenta bancaria con número de cuenta " + numeroCuenta + " no existe"));
    }

    @Override
    public Optional<Cuenta> consultarCuentaPorId(Long cuentaId) {
        return cuentaPostgreSQLGateway.obtenerCuentaPorId(cuentaId);
    }

    @Override
    public List<Cuenta> listarCuentas() {
        return cuentaPostgreSQLGateway.obtenerTodasLasCuentas();
    }

    @Override
    public Cuenta actualizarCuenta(String numeroCuenta, Cuenta cuenta) {
        Cuenta cuentaExistente = cuentaPostgreSQLGateway.obtenerCuentaPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new NotFoundException("La cuenta con número " + numeroCuenta + " no existe"));

        // Actualiza campos
        cuentaExistente.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaExistente.setEstado(cuenta.getEstado());

        return cuentaPostgreSQLGateway.actualizarCuenta(cuentaExistente);
    }
}
