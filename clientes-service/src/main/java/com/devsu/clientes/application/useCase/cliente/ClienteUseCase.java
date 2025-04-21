package com.devsu.clientes.application.useCase.cliente;

import com.devsu.clientes.domain.model.cliente.Cliente;
import com.devsu.clientes.domain.model.cliente.gateways.ClientePostgreSQLGateway;
import com.devsu.clientes.domain.model.exceptions.ClienteNotFoundException;
import com.devsu.clientes.domain.model.exceptions.DuplicatedClientException;
import com.devsu.clientes.domain.useCase.cliente.ClienteCRUDUseCase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteUseCase implements ClienteCRUDUseCase {
    private final ClientePostgreSQLGateway clientePostgreSQLGateway;

    public ClienteUseCase(ClientePostgreSQLGateway clientePostgreSQLGateway){
        this.clientePostgreSQLGateway = clientePostgreSQLGateway;
    }
    @Override
    public Cliente crearCliente(Cliente cliente) {
        Optional<Cliente> clienteExistente = clientePostgreSQLGateway.obtenerClientePorIdentificacion(cliente.getIdentificacion());
        if (clienteExistente.isPresent()) {
            throw new DuplicatedClientException("Ya existe un cliente con la identificación " + cliente.getIdentificacion());
        }
        return clientePostgreSQLGateway.crearCliente(cliente);
    }
    @Override
    public Cliente consultarClientePorId(Long id) {
        return clientePostgreSQLGateway.obtenerClientePorId(id)
                .orElseThrow(()-> new ClienteNotFoundException("Cliente con ID " + id + " no encontrado"));
    }

    @Override
    public Cliente consultarClientePorIdentificacion(String identificacion) {
        return clientePostgreSQLGateway.obtenerClientePorIdentificacion(identificacion)
                .orElseThrow(()-> new ClienteNotFoundException("Cliente con identificacion " + identificacion + " no encontrado"));
    }

    @Override
    public List<Cliente> ConsultarClientes() {
        return clientePostgreSQLGateway.obtenerTodosLosClientes();
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        Optional<Cliente> clienteExistente = clientePostgreSQLGateway.obtenerClientePorId(id);
        if (clienteExistente.isPresent()) {
            Cliente clienteDuplicado = consultarClientePorIdentificacion(cliente.getIdentificacion());
            if (clienteDuplicado != null && !clienteDuplicado.getClienteId().equals(id)) {
                throw new DuplicatedClientException("Ya existe un cliente con la identificación " + cliente.getIdentificacion());
            }
            return clientePostgreSQLGateway.actualizarCliente(id, cliente);
        }
        throw new ClienteNotFoundException("Cliente con ID " + id + " no encontrado");
    }

    @Override
    public void eliminarCliente(Long id) {
        clientePostgreSQLGateway.eliminarCliente(id);
    }
}
