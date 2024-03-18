package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {

    private TipoConta tipoConta;

    private List<Transferencia> transferencias;

    private Map<TipoCartao, List<Cartao>> cartoes;

    private String numeroConta;

    private String numeroAgencia;

    private String digitoConta;

    public Conta() {
        cartoes = new HashMap<>();
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

    @Override
    public String toString() {
        return "Conta{" +
                ", numeroConta='" + numeroConta + '\'' +
                ", numeroAgencia='" + numeroAgencia + '\'' +
                ", digitoConta='" + digitoConta + '\'' +
                '}';
    }

}
