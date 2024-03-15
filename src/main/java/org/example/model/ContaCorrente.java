package org.example.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContaCorrente extends Conta {

    private List<Transferencia> transferencias;

    private Long IdContaCorrente;

    private Long IdConta;

    private DecimalFormat saldoContaCorrente;

    private DecimalFormat taxaManutencao;

    public ContaCorrente() {
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

    public Long getIdContaCorrente() {
        return IdContaCorrente;
    }

    public void setIdContaCorrente(Long idContaCorrente) {
        IdContaCorrente = idContaCorrente;
    }

    @Override
    public Long getIdConta() {
        return IdConta;
    }

    @Override
    public void setIdConta(Long idConta) {
        IdConta = idConta;
    }

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
                "IdContaCorrente='" + IdContaCorrente + '\'' +
                ", IdConta='" + IdConta + '\'' +
                ", saldoContaCorrente='" + saldoContaCorrente + '\'' +
                ", taxaManutencao='" + taxaManutencao + '\'' +
                '}';
    }

}
