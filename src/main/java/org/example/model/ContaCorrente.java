package org.example.model;


public class ContaCorrente extends Conta {

    private Double taxaManutencao;

    public Double getTaxaManutencao() {
        return taxaManutencao;
    }

    public void setTaxaManutencao(Double taxaManutencao) {
        this.taxaManutencao = taxaManutencao;
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                ", taxaManutencao='" + taxaManutencao + '\'' +
                '}';
    }

}
