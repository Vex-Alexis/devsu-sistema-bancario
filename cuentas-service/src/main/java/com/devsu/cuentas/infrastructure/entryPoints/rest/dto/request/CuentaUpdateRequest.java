package com.devsu.cuentas.infrastructure.entryPoints.rest.dto.request;

public class CuentaUpdateRequest {
    private String tipoCuenta;
    private Boolean estado;

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
