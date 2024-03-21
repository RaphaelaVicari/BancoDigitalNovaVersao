package org.example.service;

import org.example.model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CartaoService {
    //TODO implementar {Patrick}


    public Cartao adquirirCartaoDebito(Cliente cliente, Conta conta) {
        //TODO nao esquecer de verificar o tipo do cliente para colocar os seguros obrigatorios de acordo com cada categoria

        switch (cliente.getCategoria()) {
            case SUPER:
                adquirirCartaoSuper(cliente, conta);
                break;
            case PREMIUM:
                adquirirCartaoPremium(cliente, conta);
                break;
            case COMUM:
                adquirirCartaoComun(cliente, conta);
                break;
        }

        return null;
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

        return true;
    }

    public boolean cancelarCartao(Cartao c) {

        c.setStatus(CartaoStatus.CANCELADO);
        System.out.println("Cartão cancelado com sucesso");
        return true;
    }


    private void adquirirCartaoComun(Cliente cliente, Conta conta) {

        Cartao cartaoComum = new Cartao();
        String cardNum = randomCardNumberGenerator(16);
        while (cardNum.equals(checkCardNumbers(cliente, conta))) {
            randomCardNumberGenerator(16);
        }

        cartaoComum.setNumeroCartao(cardNum);
        cartaoComum.setCvvCartao(randomCardNumberGenerator(3));
        cartaoComum.setSenhaCartao(randomCardNumberGenerator(6));
        cartaoComum.setDataVencimento(LocalDate.now().plusYears(10));
        cartaoComum.setStatus(CartaoStatus.ATIVADO);
        cartaoComum.setValorLimite(10000.00);
        try {

            conta.adicionarCartaoDebito(cartaoComum);
        } catch (Exception e) {
            System.err.println("Erro - Não foi possível criar seu cartão");

        }

    }

    private void adquirirCartaoPremium(Cliente cliente, Conta conta) {

        Cartao cartaoComum = new Cartao();
        String cardNum = randomCardNumberGenerator(16);
        while (cardNum.equals(checkCardNumbers(cliente, conta))) {
            randomCardNumberGenerator(16);
        }

        cartaoComum.setNumeroCartao(cardNum);
        cartaoComum.setCvvCartao(randomCardNumberGenerator(3));
        cartaoComum.setSenhaCartao(randomCardNumberGenerator(6));
        cartaoComum.setDataVencimento(LocalDate.now().plusYears(10));
        cartaoComum.setStatus(CartaoStatus.ATIVADO);
        cartaoComum.setValorLimite(10000.00);
        try {

            conta.adicionarCartaoDebito(cartaoComum);
        } catch (Exception e) {
            System.err.println("Erro - Não foi possível criar seu cartão");

        }


    }

    private void adquirirCartaoSuper(Cliente cliente, Conta conta) {

        Cartao cartaoComum = new Cartao();
        String cardNum = randomCardNumberGenerator(16);
        while (cardNum.equals(checkCardNumbers(cliente, conta))) {
            randomCardNumberGenerator(16);
        }

        cartaoComum.setNumeroCartao(cardNum);
        cartaoComum.setCvvCartao(randomCardNumberGenerator(3));
        cartaoComum.setSenhaCartao(randomCardNumberGenerator(6));
        cartaoComum.setDataVencimento(LocalDate.now().plusYears(10));
        cartaoComum.setStatus(CartaoStatus.ATIVADO);
        cartaoComum.setValorLimite(10000.00);
        try {

            conta.adicionarCartaoDebito(cartaoComum);
        } catch (Exception e) {
            System.err.println("Erro - Não foi possível criar seu cartão");

        }

    }

    private String randomCardNumberGenerator(int howManyNumbers) {
        Random random = new Random();
        StringBuilder numeroCartao = new StringBuilder();
        for (int i = 0; i < howManyNumbers; i++) {

            int digito = random.nextInt(10);
            numeroCartao.append(digito);
        }
        return numeroCartao.toString();
    }

    private String checkCardNumbers(Cliente cliente, Conta conta) {

        if (conta.getCartaoDebito() != null) {
            for (Cartao cartao : conta.getCartaoDebito()) {
                return cartao.getNumeroCartao();

            }
        }
        return null;
    }
}


