package com.devsu.cuentas.application.useCase.movimiento;

import com.devsu.cuentas.domain.model.cuenta.Cuenta;
import com.devsu.cuentas.domain.model.cuenta.gateways.CuentaPostgreSQLGateway;
import com.devsu.cuentas.domain.model.exceptions.InactiveAccountException;
import com.devsu.cuentas.domain.model.exceptions.InsufficientBalanceException;
import com.devsu.cuentas.domain.model.exceptions.NotFoundException;
import com.devsu.cuentas.domain.model.movimiento.Movimiento;
import com.devsu.cuentas.domain.model.movimiento.gateways.MovimientoPostgreSQLGateway;
import com.devsu.cuentas.domain.useCase.movimiento.MovimientoCRUDUseCase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class MovimientoUseCase implements MovimientoCRUDUseCase {

    private final MovimientoPostgreSQLGateway movimientoPostgreSQLGateway;
    private final CuentaPostgreSQLGateway cuentaPostgreSQLGateway;

    public MovimientoUseCase(MovimientoPostgreSQLGateway movimientoPostgreSQLGateway, CuentaPostgreSQLGateway cuentaPostgreSQLGateway) {
        this.movimientoPostgreSQLGateway = movimientoPostgreSQLGateway;
        this.cuentaPostgreSQLGateway = cuentaPostgreSQLGateway;
    }

    @Override
    public Movimiento crearMovimiento(Movimiento movimiento) {
        Cuenta cuenta = cuentaPostgreSQLGateway.obtenerCuentaPorId(movimiento.getCuentaId())
                .orElseThrow(()-> new NotFoundException("Cuenta no encontrada con ID: " + movimiento.getCuentaId()));

        if (!Boolean.TRUE.equals(cuenta.getEstado())) {
            throw new InactiveAccountException("La cuenta está inactiva. No se pueden hacer movimientos.");
        }

        double nuevoSaldo = cuenta.getSaldoInicial() + movimiento.getValor();
        if (nuevoSaldo < 0) {
            throw new InsufficientBalanceException("Saldo no disponible para realizar el movimiento.");
        }
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaPostgreSQLGateway.actualizarCuenta(cuenta); // actualiza el saldo

        movimiento.setSaldo(nuevoSaldo); // guardamos también el saldo al momento
        movimiento.setFecha(LocalDate.now());

        return movimientoPostgreSQLGateway.crearMovimiento(movimiento);
    }

    @Override
    public List<Movimiento> obtenerTodosLosMovimientos() {
        return movimientoPostgreSQLGateway.obtenerTodosLosMovimientos();
    }

    @Override
    public Optional<Movimiento> obtenerMovimientoPorId(Long movimientoId) {
        return movimientoPostgreSQLGateway.obtenerMovimientoPorId(movimientoId);
    }

    @Override
    public List<Movimiento> obtenerMovimientosPorCuentaId(Long cuentaId) {
        return movimientoPostgreSQLGateway.obtenerMovimientosPorCuentaId(cuentaId);
    }

    @Override
    public Movimiento revertirMovimiento(Long movimientoId) {
        Movimiento movimientoOriginal = movimientoPostgreSQLGateway.obtenerMovimientoPorId(movimientoId)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado"));

        // No permite revertir
        String tipoMovimiento = movimientoOriginal.getTipoMovimiento().toUpperCase();
        if (tipoMovimiento.contains("REVERTIDO") || tipoMovimiento.equals("REVERSION")) {
            throw new IllegalArgumentException("Este movimiento ya fue revertido o no se puede revertir.");
        }

        Cuenta cuenta = cuentaPostgreSQLGateway.obtenerCuentaPorId(movimientoOriginal.getCuentaId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada con ID: " + movimientoOriginal.getCuentaId()));

        if (!Boolean.TRUE.equals(cuenta.getEstado())) {
            throw new InactiveAccountException("La cuenta está inactiva. No se pueden revertir movimientos.");
        }
        // Invierte el valor del movimiento
        Double saldoActual = cuenta.getSaldoInicial();
        Double nuevoSaldo = saldoActual - movimientoOriginal.getValor();
        System.out.println("Saldo Actual: " + nuevoSaldo);
        System.out.println("Saldo Movimiento: " + movimientoOriginal.getValor());
        System.out.println("Saldo Nuevo: " + nuevoSaldo);

        // Verifica que el saldo no se vuelva negativo
        if (nuevoSaldo < 0) {
            throw new InsufficientBalanceException("No se puede revertir el movimiento, saldo insuficiente.");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaPostgreSQLGateway.actualizarCuenta(cuenta); // actualiza el saldo actual de la cuenta

        // Crea un nuevo movimiento pero de reversión
        Movimiento movimientoReversion = new Movimiento();
        movimientoReversion.setTipoMovimiento("REVERSION");
        movimientoReversion.setValor(-movimientoOriginal.getValor()); // valor invertido
        movimientoReversion.setSaldo(nuevoSaldo);
        movimientoReversion.setFecha(LocalDate.now());
        movimientoReversion.setCuentaId(cuenta.getCuentaId());

        // Marca el original como revertido
        movimientoOriginal.setTipoMovimiento("REVERTIDO: " + movimientoOriginal.getTipoMovimiento());
        movimientoPostgreSQLGateway.actualizarMovimiento(movimientoOriginal.getMovimientoId(), movimientoOriginal);

        return movimientoPostgreSQLGateway.crearMovimiento(movimientoReversion);
    }
}
