package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Seguro;
import org.example.util.FuncoesUtil;

import java.time.LocalDateTime;
import java.util.List;

public class SeguroService {

    public static final String SEGURO_VIAGEM_DESCRICAO = "Seguro Viagem com Diversas coberturas";
    public static final String SEGURO_FRAUDE_DESCRICAO = "Seguro Fraude com Diversas coberturas";

    private final ClienteService clienteRepository;

    public SeguroService(ClienteService clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Seguro criarSeguroViagemSemCobranca(Cliente cliente, Cartao cartaoCriado) {
        Seguro seguro = criarSeguro();
        seguro.setDescricaoCobertura(SEGURO_VIAGEM_DESCRICAO);
        seguro.setValorSeguro(0);

        cartaoCriado.adicionarSeguro(seguro);

        clienteRepository.atualizarCliente(cliente);
        return seguro;
    }

    private static Seguro criarSeguro() {
        Seguro seguro = new Seguro();
        seguro.setDataContratacao(LocalDateTime.now());
        seguro.setFimVigencia(LocalDateTime.now().plusYears(1));
        seguro.setInicioVigencia(LocalDateTime.now());
        seguro.setValorIndenizacao(10000);
        seguro.setNumeroApolice(FuncoesUtil.randomCardNumberGenerator(8));
        return seguro;
    }

    public Seguro criarSeguroViagemCobrado(Cliente cliente, Cartao cartaoCriado) {
        Seguro seguro = criarSeguro();
        seguro.setDescricaoCobertura(SEGURO_VIAGEM_DESCRICAO);
        seguro.setValorSeguro(50);

        cartaoCriado.adicionarSeguro(seguro);

        clienteRepository.atualizarCliente(cliente);
        return seguro;
    }

    public Seguro criarSeguroFraude(Cliente cliente, Cartao cartaoCriado) {
        Seguro seguro = criarSeguro();
        seguro.setDescricaoCobertura(SEGURO_FRAUDE_DESCRICAO);
        seguro.setValorSeguro(0);

        cartaoCriado.adicionarSeguro(seguro);

        clienteRepository.atualizarCliente(cliente);
        return seguro;
    }

    public boolean cancelarSeguro(Cliente cliente, Cartao cartao, List<Seguro> seguros, int codigoSeguro) {
        try {
            Seguro seguro = seguros.get(codigoSeguro);
            cartao.getSeguros().remove(seguro);
            clienteRepository.atualizarCliente(cliente);
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
}
