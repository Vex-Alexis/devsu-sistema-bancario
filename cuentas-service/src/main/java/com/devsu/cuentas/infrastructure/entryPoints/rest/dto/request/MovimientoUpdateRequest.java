package com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request;

public class MovimientoUpdateRequest {
    private String tipoMovimiento;
    private Double valor;

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
