package com.devsu.cuentas.application.useCase.reporte;

import com.devsu.cuentas.domain.model.cliente.Cliente;
import com.devsu.cuentas.domain.model.cliente.gateway.ClienteRestGateway;
import com.devsu.cuentas.domain.model.cuenta.Cuenta;
import com.devsu.cuentas.domain.model.cuenta.gateways.CuentaPostgreSQLGateway;
import com.devsu.cuentas.domain.model.exceptions.NotFoundException;
import com.devsu.cuentas.domain.model.movimiento.Movimiento;
import com.devsu.cuentas.domain.model.movimiento.gateways.MovimientoPostgreSQLGateway;
import com.devsu.cuentas.domain.model.reporte.CuentaReporte;
import com.devsu.cuentas.domain.model.reporte.EstadoDeCuenta;
import com.devsu.cuentas.domain.useCase.reporte.ReporteEstadoCuentaUseCase;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReporteUseCase implements ReporteEstadoCuentaUseCase {
    private final ClienteRestGateway clienteRestGateway;
    private final CuentaPostgreSQLGateway cuentaPostgreSQLGateway;
    private final MovimientoPostgreSQLGateway movimientoPostgreSQLGateway;

    public ReporteUseCase(ClienteRestGateway clienteRestGateway, CuentaPostgreSQLGateway cuentaPostgreSQLGateway, MovimientoPostgreSQLGateway movimientoPostgreSQLGateway) {
        this.clienteRestGateway = clienteRestGateway;
        this.cuentaPostgreSQLGateway = cuentaPostgreSQLGateway;
        this.movimientoPostgreSQLGateway = movimientoPostgreSQLGateway;
    }

    @Override
    public EstadoDeCuenta generarEstadoDeCuenta(String identificacionCliente, LocalDate desde, LocalDate hasta) {
        Cliente cliente = clienteRestGateway.obtenerClientePorIdentificacion(identificacionCliente);

        List<Cuenta> cuentas = cuentaPostgreSQLGateway.obtenerCuentasPorCliente(cliente.getClienteId());
        if (cuentas == null || cuentas.isEmpty()) {
            throw new RuntimeException("El cliente con identificación " + identificacionCliente + " no tiene cuentas asociadas");
        }
        List<CuentaReporte> cuentasReporte = cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientos = movimientoPostgreSQLGateway.obtenerMovimientosPorCuentaYFecha(cuenta.getCuentaId(), desde, hasta);
            return new CuentaReporte(
                    cuenta.getCuentaId(),
                    cuenta.getNumeroCuenta(),
                    cuenta.getTipoCuenta(),
                    cuenta.getSaldoInicial(),
                    cuenta.getEstado(),
                    movimientos
            );
        }).toList();

        return new EstadoDeCuenta(cliente, cuentasReporte);
    }
}
