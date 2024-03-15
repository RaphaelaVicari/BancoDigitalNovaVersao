package org.example.model;

import java.text.DecimalFormat;

public class ContaTransferencia {

    private TipoConta tipoConta;

    private String cpfCliente;

    private Long IdTransferencia;

    private String IdContaOrigem;

    private String IdContaDestino;

    private DecimalFormat valorTransferido;

    private Long tipoContaOrigem;

    private Long tipoContaDestino;

    private Long tipoTransferencia;

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Long getIdTransferencia() {
        return IdTransferencia;
    }

    public void setIdTransferencia(Long idTransferencia) {
        IdTransferencia = idTransferencia;
    }

    public String getIdContaOrigem() {
        return IdContaOrigem;
    }

    public void setIdContaOrigem(String idContaOrigem) {
        IdContaOrigem = idContaOrigem;
    }

    public String getIdContaDestino() {
        return IdContaDestino;
    }

    public void setIdContaDestino(String idContaDestino) {
        IdContaDestino = idContaDestino;
    }

    public DecimalFormat getValorTransferido() {
        return valorTransferido;
    }

    public void setValorTransferido(DecimalFormat valorTransferido) {
        this.valorTransferido = valorTransferido;
    }

    public Long getTipoContaOrigem() {
        return tipoContaOrigem;
    }

    public void setTipoContaOrigem(Long tipoContaOrigem) {
        this.tipoContaOrigem = tipoContaOrigem;
    }

    public Long getTipoContaDestino() {
        return tipoContaDestino;
    }

    public void setTipoContaDestino(Long tipoContaDestino) {
        this.tipoContaDestino = tipoContaDestino;
    }

    public Long getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(Long tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    @Override
    public String toString() {
        return "ContaTransferencia{" +
                "cpfCliente='" + cpfCliente + '\'' +
                ", IdTransferencia='" + IdTransferencia + '\'' +
                ", IdContaOrigem='" + IdContaDestino + '\'' +
                ", valorTransferido='" + valorTransferido + '\'' +
                ", tipoContaOrigem='" + tipoContaOrigem + '\'' +
                ", tipoContaDestino='" + tipoContaDestino + '\'' +
                ", tipoTransferencia='" + tipoTransferencia + '\'' +
                '}';
    }
}
