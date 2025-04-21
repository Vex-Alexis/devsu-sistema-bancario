package com.devsu.cuentas.domain.model.reporte;

import com.devsu.cuentas.domain.model.movimiento.Movimiento;

import java.util.List;

public class CuentaReporte {
    private Long cuentaId;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoActual;
    private Boolean estado;
    private List<Movimiento> movimientos;

    public CuentaReporte(Long cuentaId, String numeroCuenta, String tipoCuenta, Double saldoActual, Boolean estado, List<Movimiento> movimientos) {
        this.cuentaId = cuentaId;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoActual = saldoActual;
        this.estado = estado;
        this.movimientos = movimientos;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
