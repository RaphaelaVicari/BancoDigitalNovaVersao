package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Conta {

    private TipoConta tipoConta;

    private List<Transferencia> transferencias;

    private List<Cartao> cartoes;

    private Long IdConta;

    private String numeroConta;

    private String numeroAgencia;

    private String digitoConta;

    private String senhaConta;

    private String cpfCliente;

    private Map<TipoConta, Conta> contas;

   /* public Conta() {
        this.contas = new HashMap<>();
        this.endereco = new Endereco();
    }*/

    public Conta getContaCorrente() {
        return contas.get(TipoConta.CORRENTE);
    }

    public Conta getContaPoupanca() {
        return contas.get(TipoConta.POUPANCA);
    }

   /* public void abrirContaPoupanca(Conta conta) {
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
    }*/

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public List<Transferencia> getTransferencias() {
        return transferencias;
    }

    public void setTransferencias(List<Transferencia> transferencias) {
        this.transferencias = transferencias;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public Long getIdConta() {
        return IdConta;
    }

    public void setIdConta(Long idConta) {
        IdConta = idConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNumeroAgencia() {
        return numeroAgencia;
    }

    public void setNumeroAgencia(String numeroAgencia) {
        this.numeroAgencia = numeroAgencia;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public String getSenhaConta() {
        return senhaConta;
    }

    public void setSenhaConta(String senhaConta) {
        this.senhaConta = senhaConta;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Map<TipoConta, Conta> getContas() {
        return contas;
    }

    public void setContas(Map<TipoConta, Conta> contas) {
        this.contas = contas;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "IdConta='" + IdConta + '\'' +
                ", numeroConta='" + numeroConta + '\'' +
                ", numeroAgencia='" + numeroAgencia + '\'' +
                ", digitoConta='" + digitoConta + '\'' +
                ", senhaConta='" + senhaConta + '\'' +
                ", cpfCliente='" + cpfCliente +
                '}';
    }

}
