package org.example.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ContaPoupanca extends Conta {

    private Double saldoContaPoupanca;

    private Double taxaRendimento;

    public Double getSaldoContaPoupanca() {
        return saldoContaPoupanca;
    }

    public void setSaldoContaPoupanca(Double saldoContaPoupanca) {
        this.saldoContaPoupanca = saldoContaPoupanca;
    }

    public Double getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(Double taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
                ", saldoContaPoupanca='" + saldoContaPoupanca + '\'' +
                ", taxaRendimento='" + taxaRendimento + '\'' +
                '}';
    }

}
