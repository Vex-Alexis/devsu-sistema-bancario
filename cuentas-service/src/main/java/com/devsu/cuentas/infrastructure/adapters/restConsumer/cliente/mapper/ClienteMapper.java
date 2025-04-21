package com.devsu.cuentas.infrastructure.adapters.restConsumer.cliente.mapper;

import com.devsu.cuentas.domain.model.cliente.Cliente;
import com.devsu.cuentas.infrastructure.adapters.restConsumer.cliente.dto.ClienteResponseDTO;

public class ClienteMapper {

    public static Cliente toDomain(ClienteResponseDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(dto.getClienteId());
        cliente.setNombre(dto.getNombre());
        cliente.setDireccion(dto.getDireccion());
        cliente.setIdentificacion(dto.getIdentificacion());
        return cliente;
    }
}
