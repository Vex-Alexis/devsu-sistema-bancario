package com.devsu.clientes.domain.model.cliente.gateways;

import com.devsu.clientes.domain.model.cliente.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientePostgreSQLGateway {
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(Long id, Cliente cliente);
    Optional<Cliente> obtenerClientePorId(Long id);
    Optional<Cliente> obtenerClientePorIdentificacion(String identificacion);
    List<Cliente> obtenerTodosLosClientes();
    void eliminarCliente(Long id);
}
