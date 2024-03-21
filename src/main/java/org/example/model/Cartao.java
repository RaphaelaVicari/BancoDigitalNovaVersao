package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Cartao {

    private TipoCartao tipoCartao;

    private List<Seguro> seguros;

    private List<Pagamento> pagamentos;

    private String numeroCartao;

    private String senhaCartao;

    private String cvvCartao;

    private Double valorLimite;

    private LocalDate dataVencimento;

    private CartaoStatus status;

    public Cartao() {
        this.seguros = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }

    public CartaoStatus getStatus() {
        return status;
    }

    public void setStatus(CartaoStatus status) {
        this.status = status;
    }

    public TipoCartao getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(TipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public List<Seguro> getSeguros() {
        return seguros;
    }

    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public String getCvvCartao() {
        return cvvCartao;
    }

    public void setCvvCartao(String cvvCartao) {
        this.cvvCartao = cvvCartao;
    }

    public Double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(Double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", senhaCartao='" + senhaCartao + '\'' +
                ", cvvCartao='" + cvvCartao + '\'' +
                ", valorLimite='" + valorLimite + '\'' +
                ", dataVencimento=" + dataVencimento +
                '}';
    }

}

