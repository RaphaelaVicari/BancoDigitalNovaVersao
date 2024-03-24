package org.example.usecase;

import org.example.model.Cliente;
import org.example.dao.ClienteDao;
import org.example.security.PasswordSecurity;
import org.example.utils.FuncoesUtil;

import java.util.Scanner;


public class ClienteUseCase {

    private ClienteDao clienteDao;

    public ClienteUseCase(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public Cliente logarCliente(String loginCpf, String loginSenha) {

        Cliente clienteCadastrado;

        clienteCadastrado = clienteDao.consultarClientePorCpf(loginCpf);

        if(clienteCadastrado == null){
            return null;
        }
        if(!PasswordSecurity.checkSenha(loginSenha, clienteCadastrado.getSenhaCliente())){
            return null;
        }

        return clienteCadastrado;
    }

    public Cliente atualizarCliente(Cliente cliente) {
        return clienteDao.cadastrarCliente(cliente);
    }

    public Cliente clienteNovo(Cliente cliente) {

        if (!FuncoesUtil.validarCPF(cliente.getCpfCliente())) {
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
        return clienteDao.cadastrarCliente(cliente);

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
    public void clientPasswordUpdate(Cliente cliente, String newPassword)
    {
       cliente.setSenhaCliente(PasswordSecurity.encriptarSenha(newPassword));
       clienteDao.atualizarBaseDados();
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
        return clienteDao.consultarClientePorCpf(cpfUtil.getCPF(true));
    }

    public Cliente alterarDadosCliente(Cliente clienteAtualizado) {

        Cliente clienteExistente = clienteDao.consultarClientePorCpf(clienteAtualizado.getCpfCliente());

        if (clienteExistente != null) {
            clienteExistente.setNomeCliente(clienteAtualizado.getNomeCliente());
            clienteExistente.setDataNascimentoCliente(clienteAtualizado.getDataNascimentoCliente());
            clienteExistente.setEndereco(clienteAtualizado.getEndereco());

            clienteDao.atualizarBaseDados();

            return clienteExistente;
        } else {
            System.err.println("Cliente não encontrado para atualização.");
            return null;
        }
    }


    public boolean checkSenha(Cliente cliente, String senha) {

        return PasswordSecurity.checkSenha(senha, cliente.getSenhaCliente());

    }




}


