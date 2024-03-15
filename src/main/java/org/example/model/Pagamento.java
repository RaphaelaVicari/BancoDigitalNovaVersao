package org.example.model;

import java.time.LocalDateTime;

public class Pagamento {

    private double valor;

    private LocalDateTime dataPagamento;




    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
