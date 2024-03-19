package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ContaPoupanca extends Conta {

    private Double taxaRendimento;

    public Double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(Double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
                ", taxaRendimento='" + taxaRendimento + '\'' +
                '}';
    }

}
