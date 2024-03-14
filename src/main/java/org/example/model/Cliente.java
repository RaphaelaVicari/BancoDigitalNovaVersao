package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class Cliente {
    //TODO revisar erros
    private String cpfCliente;

    private String nomeCliente;

    private String emailCliente;

    private String numeroCelularCliente;

    private String dataNascimentoCliente;

    private String senhaCliente;

    private String enderecoCliente;

    private Double saldo;

    private CategoriaEnum categoria;

    private Endereco endereco;

    private Map<TipoConta, Conta> contas;

    public Cliente() {
        this.contas = new HashMap<>();
        this.endereco = new Endereco();
    }

    public Conta getContaCorrente() {
        return contas.get(TipoConta.CORRENTE);
    }

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

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
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

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNumeroCelularCliente() {
        return numeroCelularCliente;
    }

    public void setNumeroCelularCliente(String numeroCelularCliente) {
        this.numeroCelularCliente = numeroCelularCliente;
    }

    public String getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    public void setDataNascimentoCliente(String data) {
        this.dataNascimentoCliente = data;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpfCliente='" + cpfCliente + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", numeroCelularCliente='" + numeroCelularCliente + '\'' +
                ", dataNascimentoCliente='" + dataNascimentoCliente + '\'' +
                ", senhaCliente='" + senhaCliente + '\'' +
                ", enderecoCliente='" + enderecoCliente + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
