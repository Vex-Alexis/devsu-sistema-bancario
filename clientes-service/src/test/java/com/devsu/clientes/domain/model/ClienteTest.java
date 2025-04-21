package com.devsu.clientes.domain.model;

import com.devsu.clientes.domain.model.cliente.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    @Test
    void shouldCreateClienteWithAllFields() {
        Cliente cliente = new Cliente();
        cliente.setClienteId(1L);
        cliente.setNombre("Sofía Ramírez");
        cliente.setGenero("Femenino");
        cliente.setEdad(28);
        cliente.setIdentificacion("1122334455");
        cliente.setDireccion("Av. Siempre Viva #123");
        cliente.setTelefono("3104567890");
        cliente.setContraseña("claveSegura123");
        cliente.setEstado(true);

        assertEquals(1L, cliente.getClienteId());
        assertEquals("Sofía Ramírez", cliente.getNombre());
        assertEquals("Femenino", cliente.getGenero());
        assertEquals(28, cliente.getEdad());
        assertEquals("1122334455", cliente.getIdentificacion());
        assertEquals("Av. Siempre Viva #123", cliente.getDireccion());
        assertEquals("3104567890", cliente.getTelefono());
        assertEquals("claveSegura123", cliente.getContraseña());
        assertTrue(cliente.getEstado());
    }

    @Test
    void shouldUpdateEstadoCorrectly() {
        Cliente cliente = new Cliente();
        cliente.setEstado(false);
        assertFalse(cliente.getEstado());

        cliente.setEstado(true);
        assertTrue(cliente.getEstado());
    }

    @Test
    void shouldCompareClientesUsingEquals() {
        Cliente cliente1 = new Cliente();
        cliente1.setClienteId(1L);
        cliente1.setNombre("Juan Pérez");
        cliente1.setIdentificacion("1234567890");
        cliente1.setDireccion("Cra 12 #34-56");

        Cliente cliente2 = new Cliente();
        cliente2.setClienteId(1L);
        cliente2.setNombre("Juan Pérez");
        cliente2.setIdentificacion("1234567890");
        cliente2.setDireccion("Cra 12 #34-56");

        assertEquals(cliente1, cliente2);
        assertEquals(cliente1.hashCode(), cliente2.hashCode());
    }


}
