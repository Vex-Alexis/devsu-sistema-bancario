package com.devsu.clientes.domain.useCase.cliente;

import com.devsu.clientes.domain.model.cliente.Cliente;

import java.util.List;

public interface ClienteCRUDUseCase {
    Cliente crearCliente(Cliente cliente);
    Cliente actualizarCliente(Long id, Cliente cliente);
    Cliente consultarClientePorId(Long id);
    Cliente consultarClientePorIdentificacion(String identificacion);
    List<Cliente> ConsultarClientes();
    void eliminarCliente(Long id);
}
