package org.example.service;

import org.example.model.Cliente;
import org.example.repository.ClienteRepository;
import org.example.security.PasswordSecurity;
import org.example.util.FuncoesUtil;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
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

    public static class CPF {
        private String cpf;
        private static final String Formato = "###.###.###-##";

        public CPF(String C) {
            this.cpf = this.Format(C, false);
        }

        public boolean isCPF() {

            if (this.cpf.equals("00000000000") ||
                this.cpf.equals("11111111111") ||
                this.cpf.equals("22222222222") ||
                this.cpf.equals("33333333333") ||
                this.cpf.equals("44444444444") ||
                this.cpf.equals("55555555555") ||
                this.cpf.equals("66666666666") ||
                this.cpf.equals("77777777777") ||
                this.cpf.equals("88888888888") ||
                this.cpf.equals("99999999999") ||
                this.cpf.length() != 11)
                return (false);

            char dig10, dig11;
            int sm, i, r, num, peso;

            try {
                // Calculo do primeiro Digito Verificador
                sm = 0;
                peso = 10;
                for (i = 0; i < 9; i++) {
                    num = (int) (this.cpf.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }
                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig10 = '0';
                else
                    dig10 = (char) (r + 48);

                // Calculo do segundo Digito Verificador
                sm = 0;
                peso = 11;
                for (i = 0; i < 10; i++) {
                    num = (int) (this.cpf.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }
                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig11 = '0';
                else
                    dig11 = (char) (r + 48);

                if ((dig10 == this.cpf.charAt(9)) && (dig11 == this.cpf.charAt(10)))
                    return (true);
                else return (false);
            } catch (Exception e) {
                return (false);
            }
        }

        public String getCPF(boolean Mascara) {
            return Format(this.cpf, Mascara);
        }

        private String Format(String C, boolean Mascara) {
            if (Mascara) {
                return (C.substring(0, 3) + "." + C.substring(3, 6) + "." +
                        C.substring(6, 9) + "-" + C.substring(9, 11));
            } else {
                C = C.replace(".", "");
                C = C.replace("-", "");
                return C;
            }
        }
        public DefaultFormatterFactory getFormat() {
            try {
                return new DefaultFormatterFactory(new MaskFormatter(Formato));
            } catch (Exception e) {
                return null;
            }
        }

    }

    public Cliente clienteNovo(Cliente cliente) {

        if (validarCPF(cliente)) {
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
        CPF cpfUtil = new CPF(cpf);
        if(!cpfUtil.isCPF()){
            System.err.println("CPF inválido");
            return null;
        }
        return clienteRepository.consultarClientePorCpf(cpfUtil.getCPF(true));
    }

   private boolean validarCPF(Cliente cliente) {
        CPF cpfUtil = new CPF(cliente.getCpfCliente());

        if (!cpfUtil.isCPF()) {
            return false;
        }

        cliente.setCpfCliente(cpfUtil.getCPF(true));
        return true;
    }

    public boolean checkSenha(Cliente cliente, String senha) {

        return PasswordSecurity.checkSenha(senha, cliente.getSenhaCliente());

    }


}


