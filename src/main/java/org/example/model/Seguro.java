package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        return "Seguro " + descricaoCobertura +
                "\nNúmero da Apolice: " + numeroApolice + "\n" +
                "Cobertura: " + descricaoCobertura + "\n" +
                "Valor Seguro: " + valorSeguro + "\n" +
                "Valor Indenização: " + valorIndenizacao + "\n" +
                "Data Contratação: " + dataContratacao.format(DateTimeFormatter.ISO_DATE) + "\n" +
                "Inicio Vigência: " + inicioVigencia.format(DateTimeFormatter.ISO_DATE) + "\n" +
                "Fim Vigência: " + fimVigencia.format(DateTimeFormatter.ISO_DATE);
    }
}
