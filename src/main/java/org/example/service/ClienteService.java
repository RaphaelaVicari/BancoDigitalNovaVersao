package org.example.service;

import org.example.model.Cliente;
import org.example.repository.ClienteRepository;
import org.example.security.PasswordSecurity;
import org.example.util.FuncoesUtil;

import java.util.Scanner;


public class ClienteService {

    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente logarCliente(String loginCpf, String loginSenha) {

        Cliente clienteCadastrado;

        clienteCadastrado = clienteRepository.consultarClientePorCpf(loginCpf);

        if(clienteCadastrado == null){
            return null;
        }
        //todo validar metodo de login porque senha nao esta batendo
        if(!PasswordSecurity.checkSenha(loginSenha, clienteCadastrado.getSenhaCliente())){
            return null;
        }

        return clienteCadastrado;
    }

    public Cliente clienteNovo(Cliente cliente) {

        if (!FuncoesUtil.validarCPF(String.valueOf(cliente))) {
            System.err.println("CPF inválido! Não foi possível cadastrar o cliente.");
            return null;
        }


        if (cpfJaCadastrado(cliente.getCpfCliente())) {
            System.err.println("CPF já cadastrado! Não é possível cadastrar novamente.");
            return null;
        }

        if (cliente.getNomeCliente().trim().equals("")) {
            System.err.println("Não e possivel cadastrar com o campo nome vazio!");
            return null;
        }


        if (cliente.getSenhaCliente().trim().equals("")) {
            System.err.println("Não e possivel cadastrar com o campo senha vazio!");
            return null;
        }
        
        cliente.setSenhaCliente(PasswordSecurity.encriptarSenha(cliente.getSenhaCliente()));
        return clienteRepository.cadastrarCliente(cliente);

    }

    public String cadastroSenha(Scanner scanner, String senha, String senhaConfirm) {
        if (senha.equals(senhaConfirm)) {
            return senha;
        } else {
            System.err.println("As senhas não estão iguais.\n Digite novamente");
            senha = scanner.next();
            senhaConfirm = scanner.next();
            return cadastroSenha(scanner, senha, senhaConfirm);
        }
    }

    public boolean cpfJaCadastrado(String cpf) {

        if (consultarClientePorCpf(cpf) != null) {
            return true;
        } else {
            return false;
        }
    }

    public Cliente consultarClientePorCpf(String cpf) {
        FuncoesUtil.CPF cpfUtil = new FuncoesUtil.CPF(cpf);
        if(!cpfUtil.isCPF()){
            System.err.println("CPF inválido");
            return null;
        }
        return clienteRepository.consultarClientePorCpf(cpfUtil.getCPF(true));
    }

    public boolean checkSenha(Cliente cliente, String senha) {

        return PasswordSecurity.checkSenha(senha, cliente.getSenhaCliente());

    }
}


