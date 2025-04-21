package com.devsu.cuentas.domain.model.cliente.gateway;

import com.devsu.cuentas.domain.model.cliente.Cliente;

public interface ClienteRestGateway {
    Cliente obtenerClientePorIdentificacion(String identificacion);
}
