package com.devsu.clientes.infrastructure.adapters.postgresql.adapter;

import com.devsu.clientes.domain.model.cliente.Cliente;
import com.devsu.clientes.domain.model.cliente.gateways.ClientePostgreSQLGateway;
import com.devsu.clientes.infrastructure.adapters.postgresql.entity.ClienteEntity;
import com.devsu.clientes.infrastructure.adapters.postgresql.repository.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ClienteAdapter implements ClientePostgreSQLGateway {

    private final ClienteRepository clienteRepository;

    public ClienteAdapter(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        ClienteEntity entity = mapToEntity(cliente);
        return mapToDomain(clienteRepository.save(entity));
    }

    @Override
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id)
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Cliente> obtenerClientePorIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion)
                .map(this::mapToDomain);
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Cliente actualizarCliente(Long id, Cliente cliente) {
        ClienteEntity existente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        existente.setNombre(cliente.getNombre());
        existente.setGenero(cliente.getGenero());
        existente.setEdad(cliente.getEdad());
        existente.setIdentificacion(cliente.getIdentificacion());
        existente.setDireccion(cliente.getDireccion());
        existente.setTelefono(cliente.getTelefono());
        existente.setContrasena(cliente.getContraseña());
        existente.setEstado(cliente.getEstado());

        return mapToDomain(clienteRepository.save(existente));
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.deleteById(id);
    }

    // ========== MÉTODOS DE MAPEOS ==========

    private ClienteEntity mapToEntity(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setClienteId(cliente.getClienteId());
        entity.setNombre(cliente.getNombre());
        entity.setGenero(cliente.getGenero());
        entity.setEdad(cliente.getEdad());
        entity.setIdentificacion(cliente.getIdentificacion());
        entity.setDireccion(cliente.getDireccion());
        entity.setTelefono(cliente.getTelefono());
        entity.setContrasena(cliente.getContraseña());
        entity.setEstado(cliente.getEstado());
        return entity;
    }

    private Cliente mapToDomain(ClienteEntity entity) {
        Cliente cliente = new Cliente();
        cliente.setClienteId(entity.getClienteId());
        cliente.setNombre(entity.getNombre());
        cliente.setGenero(entity.getGenero());
        cliente.setEdad(entity.getEdad());
        cliente.setIdentificacion(entity.getIdentificacion());
        cliente.setDireccion(entity.getDireccion());
        cliente.setTelefono(entity.getTelefono());
        cliente.setContraseña(entity.getContrasena());
        cliente.setEstado(entity.getEstado());
        return cliente;
    }
}
