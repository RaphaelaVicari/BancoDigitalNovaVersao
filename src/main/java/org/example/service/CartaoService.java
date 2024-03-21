package org.example.service;

import org.example.model.Cartao;
import org.example.model.Cliente;
import org.example.model.Conta;

public class CartaoService {
    //TODO implementar {Patrick}
    public Cartao adquirirCartaoDebito(Cliente cliente, Conta conta) {
        //TODO nao esquecer de verificar o tipo do cliente para colocar os seguros obrigatorios de acordo com cada categoria
        return null;
    }

    public boolean alterarLimiteDiario(Cliente cliente, Cartao c, double novoLimite) {

        switch(cliente.getCategoria()){
            case SUPER:
            break;
            case COMUM:
            break;
            case PREMIUM:
            break;
        }

        return true;
    }

    public boolean cancelarCartao(Cartao c) {
        return true;
    }
}
