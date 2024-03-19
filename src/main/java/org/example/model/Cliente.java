package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {
    private String cpfCliente;

    private String nomeCliente;

    private LocalDate dataNascimentoCliente;

    private String senhaCliente;

    private CategoriaEnum categoria;

    private Endereco endereco;
//todo remover map tipo conta e criar duas instancias uma para cada tipo de conta
    private Map<TipoConta, Conta> contas;

    public Cliente() {
        this.contas = new HashMap<>();
        this.endereco = new Endereco();
    }

    @JsonIgnore
    public Conta getContaCorrente() {
        return contas.get(TipoConta.CORRENTE);
    }

    @JsonIgnore
    public Conta getContaPoupanca() {
        return contas.get(TipoConta.POUPANCA);
    }

    public void abrirContaPoupanca(Conta conta) {
        if (contas.containsKey(TipoConta.POUPANCA)) {
            System.err.println("Erro, este cliente ja possui conta poupança!");
            return;
        }

        abrirConta(conta, TipoConta.POUPANCA);
    }

    public void abrirContaCorrente(Conta conta) {
        if (contas.containsKey(TipoConta.CORRENTE)) {
            System.err.println("Erro, este cliente já possui conta corrente!");
            return;
        }

        abrirConta(conta, TipoConta.CORRENTE);
    }

    private void abrirConta(Conta conta, TipoConta tipoConta) {
        contas.put(tipoConta, conta);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public CategoriaEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEnum categoria) {
        this.categoria = categoria;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public LocalDate getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    public void setDataNascimentoCliente(LocalDate data) {
        this.dataNascimentoCliente = data;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }

    public Map<TipoConta, Conta> getContas() {
        return contas;
    }
}
