package org.example.model;

import java.time.LocalDateTime;

public class Seguro {

    private String numeroApolice;

    private String descricaoCobertura;

    private Double valorSeguro;

    private Double valorIndenizacao;

    private LocalDateTime dataContratacao;

    private LocalDateTime inicioVigencia;

    private LocalDateTime fimVigencia;

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public String getDescricaoCobertura() {
        return descricaoCobertura;
    }

    public void setDescricaoCobertura(String descricaoCobertura) {
        this.descricaoCobertura = descricaoCobertura;
    }

    public double getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(double valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public double getValorIndenizacao() {
        return valorIndenizacao;
    }

    public void setValorIndenizacao(double valorIndenizacao) {
        this.valorIndenizacao = valorIndenizacao;
    }

    public LocalDateTime getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDateTime dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public LocalDateTime getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(LocalDateTime inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public LocalDateTime getFimVigencia() {
        return fimVigencia;
    }

    public void setFimVigencia(LocalDateTime fimVigencia) {
        this.fimVigencia = fimVigencia;
    }
}
