package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {

    private TipoConta tipoConta;

    private List<Transferencia> transferencias;

    private Map<TipoCartao, List<Cartao>> cartoes;

    private String numeroConta;

    private Double saldo;

    private String numeroAgencia;

    private String digitoConta;

    public Conta() {
        cartoes = new HashMap<>();
        cartoes.put(TipoCartao.CREDITO, new ArrayList<>());
        cartoes.put(TipoCartao.DEBITO, new ArrayList<>());
        transferencias = new ArrayList<>();
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Conta{" +
                ", numeroConta='" + numeroConta + '\'' +
                ", numeroAgencia='" + numeroAgencia + '\'' +
                ", digitoConta='" + digitoConta + '\'' +
                '}';
    }
    public Map<TipoCartao, List<Cartao>> getCartoes() {
        return cartoes;
    }

    public boolean adicionarCartaoCredito(Cartao cartao) {
        return adicionarCartao(TipoCartao.CREDITO, cartao);
    }

    public boolean adicionarCartaoDebito(Cartao cartao) {
        return adicionarCartao(TipoCartao.DEBITO, cartao);
    }

    @JsonIgnore
    public List<Cartao> getCartaoDebito(){
        return cartoes.get(TipoCartao.DEBITO);
    }
    @JsonIgnore
    public List<Cartao> getCartaoCredito() {
        return cartoes.get(TipoCartao.CREDITO);
    }

    private boolean adicionarCartao(TipoCartao debito, Cartao cartao) {
        List<Cartao> cartoes = this.cartoes.get(debito);
        boolean res = cartoes.add(cartao);
        this.cartoes.put(debito, cartoes);
        return res;
    }

}
