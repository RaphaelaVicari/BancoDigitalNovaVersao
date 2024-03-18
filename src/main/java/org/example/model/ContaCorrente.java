package org.example.model;


public class ContaCorrente extends Conta {

    private Double saldoContaCorrente;

    private Double taxaManutencao;

    public Double getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public void setSaldoContaCorrente(Double saldoContaCorrente) {
        this.saldoContaCorrente = saldoContaCorrente;
    }

    public Double getTaxaManutencao() {
        return taxaManutencao;
    }

    public void setTaxaManutencao(Double taxaManutencao) {
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
