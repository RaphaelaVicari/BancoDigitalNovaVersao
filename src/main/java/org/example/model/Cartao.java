package org.example.model;

import java.time.LocalDateTime;
import java.util.*;

public class Cartao {

    private TipoCartao tipoCartao;

    private List<Seguro> seguros;

    private List<Pagamento> pagamentos;

    private Long IdCartao;

    private Long IdConta;

    private Long IdTipoCartao;

    private String numeroCartao;

    private String senhaCartao;

    private String cvvCartao;

    private Double valorLimite;

    private LocalDateTime dataVencimento;

    private Map<TipoCartao, Cartao> cartao;

    public Cartao() {
        this.seguros = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
    }

    public Cartao getCartaoCredito() {
        return cartao.get(TipoCartao.CREDITO);
    }

    public Cartao getCartaoDebito() {
        return cartao.get(TipoCartao.DEBITO);
    }

   /* public void solicitarCartaoCredito(Cartao cartao) {
        if (cartao.equals(TipoCartao.CREDITO)) {
            System.err.println("Erro, voce ja possui cartão de credito!");
            return;
        }

        gerarCartao(cartao, TipoCartao.CREDITO);
    }

    public void solicitarCartaoDebito(Cartao cartao) {
        if (cartao.equals(TipoCartao.DEBITO)) {
            System.err.println("Erro,  voce ja possui cartão de debito!");
            return;
        }

        gerarCartao(cartao, TipoCartao.DEBITO);
    }

    private void gerarCartao(Cartao cartao, TipoCartao tipoCartao) {
        cartao.put(tipoCartao, cartao);
    }

    */
    public TipoCartao getTipoCartao() {
        return tipoCartao; }

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

    public Long getIdCartao() {
        return IdCartao;
    }

    public void setIdCartao(Long idCartao) {
        IdCartao = idCartao;
    }

    public Long getIdConta() {
        return IdConta;
    }

    public void setIdConta(Long idConta) {
        IdConta = idConta;
    }

    public Long getIdTipoCartao() {
        return IdTipoCartao;
    }

    public void setIdTipoCartao(Long idTipoCartao) {
        IdTipoCartao = idTipoCartao;
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

    public LocalDateTime getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Map<TipoCartao, Cartao> getCartao() {
        return cartao;
    }

    public void setCartao(Map<TipoCartao, Cartao> cartao) {
        this.cartao = cartao;
    }

    @Override
    public String toString() {
        return "Cartao{" +
                "IdCartao='" + IdCartao + '\'' +
                ", IdConta='" + IdConta + '\'' +
                ", IdTipoCartao='" + IdTipoCartao + '\'' +
                ", numeroCartao='" + numeroCartao + '\'' +
                ", senhaCartao='" + senhaCartao + '\'' +
                ", cvvCartao='" + cvvCartao + '\'' +
                ", valorLimite='" + valorLimite + '\'' +
                ", dataVencimento=" + dataVencimento +
                '}';
    }

}

