package com.devsu.cuentas.infrastructure.adapters.postgresql.adapter;

import com.devsu.cuentas.domain.model.cuenta.Cuenta;
import com.devsu.cuentas.domain.model.cuenta.gateways.CuentaPostgreSQLGateway;
import com.devsu.cuentas.infrastructure.adapters.postgresql.entity.CuentaEntity;
import com.devsu.cuentas.infrastructure.adapters.postgresql.repository.CuentaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CuentaAdapter implements CuentaPostgreSQLGateway {

    private final CuentaRepository cuentaRepository;

    public CuentaAdapter(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        CuentaEntity entity = mapToEntity(cuenta);
        return mapToDomain(cuentaRepository.save(entity));
    }

    @Override
    public Optional<Cuenta> obtenerCuentaPorId(Long cuentaId) {
        return cuentaRepository.findById(cuentaId)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Cuenta> obtenerCuentaPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .map(this::mapToDomain);
    }

    @Override
    public List<Cuenta> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Cuenta> obtenerCuentasPorCliente(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Cuenta actualizarCuenta(Cuenta cuenta) {
        CuentaEntity entity = mapToEntity(cuenta);
        CuentaEntity actualizada = cuentaRepository.save(entity);
        return mapToDomain(actualizada);
    }


    // ========== MÉTODOS DE MAPEOS ==========

    public CuentaEntity mapToEntity(Cuenta cuenta) {
        CuentaEntity entity = new CuentaEntity();
        entity.setCuentaId(cuenta.getCuentaId());
        entity.setNumeroCuenta(cuenta.getNumeroCuenta());
        entity.setTipoCuenta(cuenta.getTipoCuenta());
        entity.setSaldoInicial(cuenta.getSaldoInicial());
        entity.setEstado(cuenta.getEstado());
        entity.setClienteId(cuenta.getClienteId());
        return entity;
    }

    public Cuenta mapToDomain(CuentaEntity entity) {
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(entity.getCuentaId());
        cuenta.setNumeroCuenta(entity.getNumeroCuenta());
        cuenta.setTipoCuenta(entity.getTipoCuenta());
        cuenta.setSaldoInicial(entity.getSaldoInicial());
        cuenta.setEstado(entity.getEstado());
        cuenta.setClienteId(entity.getClienteId());
        return cuenta;
    }
}
