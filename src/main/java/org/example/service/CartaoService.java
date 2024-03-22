package org.example.service;

import org.example.model.*;
import org.example.util.FuncoesUtil;

import java.time.LocalDate;
import java.util.Random;

import static org.example.util.FuncoesUtil.randomCardNumberGenerator;

public class CartaoService {
    private final ClienteService clienteRepository;

    public CartaoService(ClienteService clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    //TODO implementar {Patrick}

    public Cartao adquirirCartaoDebito(Cliente cliente, Conta conta) {
        //TODO nao esquecer de verificar o tipo do cliente para colocar os seguros obrigatorios de acordo com cada categoria

        switch (cliente.getCategoria()) {
            case SUPER:
                return adquirirCartaoDebito(cliente, conta, 10000d);
            case PREMIUM:
                return adquirirCartaoDebito(cliente, conta, 5000d);
            case COMUM:
                return adquirirCartaoDebito(cliente, conta, 1000d);
            default:
                System.err.println("Cliente sem categoria para criação de cartao de débito");
                return null;
        }
    }

    public Cartao adquirirCartaoCredito(Cliente cliente, Conta conta) {
        //TODO nao esquecer de verificar o tipo do cliente para colocar os seguros obrigatorios de acordo com cada categoria

        switch (cliente.getCategoria()) {
            case SUPER:
                return adquirirCartaoCredito(cliente, conta, 10000d);
            case PREMIUM:
                return adquirirCartaoCredito(cliente, conta, 5000d);
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
                System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                System.out.println("Valor minímo: R$1000,00 , Valor máximo: SEM LIMITE");
                if (novoLimite >= 1000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
            case PREMIUM:
                System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                System.out.println("Valor minímo: R$1000,00 , Valor máximo: R$30.000,00");
                if (novoLimite >= 1000 && novoLimite<=30000)
                    c.setValorLimite(novoLimite);
                else
                    return false;
                break;
            case COMUM:
                System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                System.out.println("Valor minímo: R$1000,00 , Valor máximo: R$15.000,00");
                if (novoLimite >= 1000 && novoLimite<=15000)
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


