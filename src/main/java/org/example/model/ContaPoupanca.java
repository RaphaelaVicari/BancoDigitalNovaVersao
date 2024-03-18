package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ContaPoupanca extends Conta {

        private List<Transferencia> transferencias;

        private Double saldoContaPoupanca;

        private Double taxaRendimento;

        public ContaPoupanca() {
            this.transferencias = new ArrayList<>();
        }

    @Override
    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    @Override
    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

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
