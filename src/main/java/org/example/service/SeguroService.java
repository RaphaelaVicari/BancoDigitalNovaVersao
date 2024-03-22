package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Seguro;
import org.example.util.FuncoesUtil;

import java.time.LocalDateTime;

public class SeguroService {

    private final ClienteService clienteRepository;

    public SeguroService(ClienteService clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Seguro criarSeguroViagemSemCobranca(Cliente cliente, Cartao cartaoCriado) {
        Seguro seguro = criarSeguro();
        seguro.setDescricaoCobertura("Seguro Viagem com Diversas coberturas");
        seguro.setValorSeguro(0);

        cartaoCriado.adicionarSeguro(seguro);

        clienteRepository.atualizarCliente(cliente);
        return seguro;
    }

    private static Seguro criarSeguro() {
        Seguro seguro = new Seguro();
        seguro.setDataContratacao(LocalDateTime.now());
        seguro.setFimVigencia(LocalDateTime.now().plusYears(1));
        seguro.setValorIndenizacao(10000);
        seguro.setNumeroApolice(FuncoesUtil.randomCardNumberGenerator(8));
        return seguro;
    }

    public Seguro criarSeguroViagemCobrado(Cliente cliente, Cartao cartaoCriado) {
        Seguro seguro = criarSeguro();
        seguro.setDescricaoCobertura("Seguro Viagem com Diversas coberturas");
        seguro.setValorSeguro(50);

        cartaoCriado.adicionarSeguro(seguro);

        clienteRepository.atualizarCliente(cliente);
        return seguro;
    }

    public Seguro criarSeguroFraude(Cliente cliente, Cartao cartaoCriado) {
        Seguro seguro = criarSeguro();
        seguro.setDescricaoCobertura("Seguro Fraude com Diversas coberturas");
        seguro.setValorSeguro(0);

        cartaoCriado.adicionarSeguro(seguro);

        clienteRepository.atualizarCliente(cliente);
        return seguro;
    }
}
