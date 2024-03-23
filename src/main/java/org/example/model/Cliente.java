package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Cliente {
    private String cpfCliente;

    private String nomeCliente;

    private LocalDate dataNascimentoCliente;

    private String senhaCliente;

    private CategoriaEnum categoria;

    private Endereco endereco;

    private ContaCorrente contaCorrente;

    private ContaPoupanca contaPoupanca;

    public Cliente() {
        this.endereco = new Endereco();
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca;
    }

    public void setContaPoupanca(ContaPoupanca conta) {
        this.contaPoupanca = conta;
    }

    public void setContaCorrente(ContaCorrente conta) {
        this.contaCorrente = conta;
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

    @Override
    public String toString() {
        return
                "CATEGORIA: " + categoria +"\n" +
                "CPF: " + cpfCliente +"\n" +
                "NOME: " + nomeCliente +"\n" +
                "DATA DE NASCIMENTO: " + dataNascimentoCliente +"\n" +
                "ENDEREÇO: " + "\n" + endereco;

    }
}
