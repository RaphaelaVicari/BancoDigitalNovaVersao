package org.example.usecase;

import org.example.model.*;

import java.time.LocalDate;

import static org.example.utils.FuncoesUtil.randomCardNumberGenerator;

public class CartaoUseCase {
    private final ClienteUseCase clienteRepository;

    public CartaoUseCase(ClienteUseCase clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cartao adquirirCartaoDebito(Cliente cliente, Conta conta) {

        switch (cliente.getCategoria()) {
            case SUPER:
                return adquirirCartaoDebito(cliente, conta, 5000d);
            case PREMIUM:
                return adquirirCartaoDebito(cliente, conta, 10000d);
            case COMUM:
                return adquirirCartaoDebito(cliente, conta, 1000d);
            default:
                System.err.println("Cliente sem categoria para criação de cartao de débito");
                return null;
        }
    }

    public Cartao adquirirCartaoCredito(Cliente cliente, Conta conta) {

        switch (cliente.getCategoria()) {
            case SUPER:
                return adquirirCartaoCredito(cliente, conta, 5000d);
            case PREMIUM:
                return adquirirCartaoCredito(cliente, conta, 10000d);
            case COMUM:
                return adquirirCartaoCredito(cliente, conta, 1000d);
            default:
                System.err.println("Cliente sem categoria para criação de cartao de débito");
                return null;
        }
    }

    private Cartao adquirirCartaoCredito(Cliente cliente, Conta conta, double valorLimite) {
        Cartao cartao = adquirirCartao(conta, TipoCartao.CREDITO, valorLimite);
        conta.adicionarCartaoCredito(cartao);
        clienteRepository.atualizarCliente(cliente);
        return cartao;
    }

    private Cartao adquirirCartaoDebito(Cliente cliente, Conta conta, double valorLimite) {
        Cartao cartao = adquirirCartao(conta, TipoCartao.DEBITO, valorLimite);
        conta.adicionarCartaoDebito(cartao);
        clienteRepository.atualizarCliente(cliente);
        return cartao;
    }

    private Cartao adquirirCartao(Conta conta,
                                TipoCartao tipo,
                                double valorLimite) {

        Cartao cartaoComum = new Cartao();
        String cardNum = randomCardNumberGenerator(16);
        while (cardNum.equals(checkCardNumbers(conta))) {
            cardNum = randomCardNumberGenerator(16);
        }

        cartaoComum.setNumeroCartao(cardNum);
        cartaoComum.setCvvCartao(randomCardNumberGenerator(3));
        cartaoComum.setSenhaCartao(randomCardNumberGenerator(6));
        cartaoComum.setDataVencimento(LocalDate.now().plusYears(10));
        cartaoComum.setStatus(CartaoStatus.ATIVADO);

        cartaoComum.setTipoCartao(tipo);
        cartaoComum.setValorLimite(valorLimite);

        return cartaoComum;
    }

    public boolean alterarLimiteDiario(Cliente cliente, Cartao c, double novoLimite) {

        switch (cliente.getCategoria()) {
            case SUPER:
                if (novoLimite >= 1000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
            case PREMIUM:
                if (novoLimite >= 1000 && novoLimite<=30000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
            case COMUM:
                if (novoLimite >= 1000 && novoLimite<=15000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
        }

        clienteRepository.atualizarCliente(cliente);
        return true;
    }
    public boolean alterarLimiteCredito(Cliente cliente, Cartao c, double novoLimite) {

        switch (cliente.getCategoria()) {
            case SUPER:
                if (novoLimite >= 5000 && novoLimite <= 10000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
            case PREMIUM:
                if (novoLimite >= 10000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
            case COMUM:
                if (novoLimite >= 1000 && novoLimite <= 5000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
        }

        clienteRepository.atualizarCliente(cliente);
        return true;
    }



    public boolean cancelarCartao(Cliente cliente, Cartao c) {
        c.setStatus(CartaoStatus.CANCELADO);
        System.out.println("Cartão cancelado com sucesso");
        clienteRepository.atualizarCliente(cliente);
        return true;
    }


    private String checkCardNumbers(Conta conta) {

        if (conta.getCartaoDebito() != null) {
            for (Cartao cartao : conta.getCartaoDebito()) {
                return cartao.getNumeroCartao();

            }
        }
        return null;
    }
}


