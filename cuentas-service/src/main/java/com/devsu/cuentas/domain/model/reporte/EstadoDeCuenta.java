package com.devsu.cuentas.domain.model.reporte;

import com.devsu.cuentas.domain.model.cliente.Cliente;
import com.devsu.cuentas.domain.model.movimiento.Movimiento;
import lombok.AllArgsConstructor;

import java.util.List;

public class EstadoDeCuenta {
    private Cliente cliente;
    private List<CuentaReporte> cuentas;

    public EstadoDeCuenta(Cliente cliente, List<CuentaReporte> cuentas) {
        this.cliente = cliente;
        this.cuentas = cuentas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<CuentaReporte> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaReporte> cuentas) {
        this.cuentas = cuentas;
    }
}
