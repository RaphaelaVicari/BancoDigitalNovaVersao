package org.example.model;

import java.text.DecimalFormat;

public class ContaCorrente extends Conta {

    private DecimalFormat saldoContaCorrente;

    private DecimalFormat taxaManutencao;

    public DecimalFormat getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public void setSaldoContaCorrente(DecimalFormat saldoContaCorrente) {
        this.saldoContaCorrente = saldoContaCorrente;
    }

    public DecimalFormat getTaxaManutencao() {
        return taxaManutencao;
    }

    public void setTaxaManutencao(DecimalFormat taxaManutencao) {
        this.taxaManutencao = taxaManutencao;
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                ", saldoContaCorrente='" + saldoContaCorrente + '\'' +
                ", taxaManutencao='" + taxaManutencao + '\'' +
                '}';
    }

}
