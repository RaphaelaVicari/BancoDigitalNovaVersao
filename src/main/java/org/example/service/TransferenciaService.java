package org.example.service;

import org.example.repository.ClienteRepository;

public class TransferenciaService {
    private final ClienteRepository clienteRepository;

    public TransferenciaService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
}
