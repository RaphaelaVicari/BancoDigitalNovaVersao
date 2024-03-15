package org.example.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ContaPoupanca extends Conta {

        private List<Transferencia> transferencias;
        private Long IdContaPoupanca;
        private Long IdConta;

        private DecimalFormat saldoContaPoupanca;

        private DecimalFormat taxaRendimento;

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

    public Long getIdContaPoupança() {
        return IdContaPoupanca;
    }

    public void setIdContaPoupanca(Long idContaPoupanca) {
        IdContaPoupanca = idContaPoupanca;
    }

    @Override
    public Long getIdConta() {
        return IdConta;
    }

    @Override
    public void setIdConta(Long idConta) {
        IdConta = idConta;
    }

    public DecimalFormat getSaldoContaPoupanca() {
        return saldoContaPoupanca;
    }

    public void setSaldoContaPoupanca(DecimalFormat saldoContaPoupanca) {
        this.saldoContaPoupanca = saldoContaPoupanca;
    }

    public DecimalFormat getTaxaRendimento() {
        return taxaRendimento;
    }

    public void setTaxaRendimento(DecimalFormat taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    @Override
        public String toString() {
            return "ContaPoupanca{" +
                    "IdContaPoupanca='" + IdContaPoupanca + '\'' +
                    ", IdConta='" + IdConta + '\'' +
                    ", saldoContaPoupanca='" + saldoContaPoupanca + '\'' +
                    ", taxaRendimento='" + taxaRendimento + '\'' +
                    '}';
        }

    }
