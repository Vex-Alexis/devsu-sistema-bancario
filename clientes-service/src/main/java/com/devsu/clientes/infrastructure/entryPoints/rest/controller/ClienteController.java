package com.devsu.clientes.infrastructure.entryPoints.rest.controller;

import com.devsu.clientes.application.useCase.cliente.ClienteUseCase;
import com.devsu.clientes.domain.model.cliente.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;

    public ClienteController(ClienteUseCase crearClienteUseCase) {
        this.clienteUseCase = crearClienteUseCase;
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteUseCase.crearCliente(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> consultarClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteUseCase.consultarClientePorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<Cliente> obtenerCliente(@PathVariable String identificacion) {
        Cliente cliente = clienteUseCase.consultarClientePorIdentificacion(identificacion);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> consultarClientes() {
        List<Cliente> listaClientes = clienteUseCase.ConsultarClientes();
        return ResponseEntity.status(HttpStatus.OK).body(listaClientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteUseCase.actualizarCliente(id, cliente);
        return ResponseEntity.status(HttpStatus.OK).body(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteUseCase.eliminarCliente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }






}
