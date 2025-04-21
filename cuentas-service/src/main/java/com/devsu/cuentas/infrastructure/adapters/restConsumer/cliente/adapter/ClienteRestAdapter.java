package com.devsu.cuentas.infrastructure.adapters.restConsumer.cliente.adapter;

import com.devsu.cuentas.domain.model.cliente.Cliente;
import com.devsu.cuentas.domain.model.cliente.gateway.ClienteRestGateway;
import com.devsu.cuentas.domain.model.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class ClienteRestAdapter implements ClienteRestGateway {


    private String clientesBaseUrl;
    private final RestTemplate restTemplate;

    public ClienteRestAdapter(
            @Value("${clientes.base-url}") String clientesUrlTemplate,
            RestTemplate restTemplate) {
        this.clientesBaseUrl = clientesUrlTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public Cliente obtenerClientePorIdentificacion(String identificacion) {
        String url = clientesBaseUrl + identificacion;
        try {
            return restTemplate.getForObject(url, Cliente.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new NotFoundException("No se encontró un cliente con identificación " + identificacion);
        } catch (ResourceAccessException ex) {
            System.out.println("Excepcion: " + ex + ", Message: " + ex.getMessage());
            throw new RuntimeException("No se pudo conectar al servicio de clientes", ex);
        }
    }
}
