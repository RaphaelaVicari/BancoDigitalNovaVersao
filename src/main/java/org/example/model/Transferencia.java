package org.example.model;

import java.time.LocalDateTime;

public class Transferencia {

    private ContaTransferencia contaOrigem;

    private ContaTransferencia contaDestino;

    private Double valorTransferido;

    private TipoTransferencia tipoTransferencia;

    private LocalDateTime dataTransferencia;

    public ContaTransferencia getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(ContaTransferencia contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public ContaTransferencia getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(ContaTransferencia contaDestino) {
        this.contaDestino = contaDestino;
    }

    public Double getValorTransferido() {
        return valorTransferido;
    }

    public void setValorTransferido(Double valorTransferido) {
        this.valorTransferido = valorTransferido;
    }

    public TipoTransferencia getTipoTransferencia() {
        return tipoTransferencia;
    }

    public void setTipoTransferencia(TipoTransferencia tipoTransferencia) {
        this.tipoTransferencia = tipoTransferencia;
    }

    public LocalDateTime getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDateTime dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }
}
