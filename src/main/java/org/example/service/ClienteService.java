package org.example.service;

import org.example.model.Cliente;
import org.example.repository.ClienteRepository;
import org.example.security.PasswordSecurity;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClienteService {

    private ClienteRepository clienteRepository;
    private PasswordSecurity passwordSecurity;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
        this.passwordSecurity = new PasswordSecurity();
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

    }

    public Cliente clienteNovo(Cliente cliente) {

        if (!validarCPF(cliente)) {
            System.err.println("CPF inválido! Não foi possível cadastrar o cliente.");
            return null;
        }

        if (!validarEmail(cliente.getEmailCliente())) {
            System.err.println("E-mail inválido! Não foi possível cadastrar o cliente.");
            return null;
        }

        if (!validarDataNascimento(cliente.getDataNascimentoCliente())) {
            System.err.println("Data de Nascimento inválido ou campo vazio! Não foi possível cadastrar o cliente.");
            return null;
        }

        if (!validarNumeroCelular(cliente.getNumeroCelularCliente())) {
            System.err.println("Número de celular inválido ou campo vazio! Não foi possível cadastrar o cliente.");
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

        if (cliente.getEnderecoCliente().trim().equals("")) {
            System.err.println("Não e possivel cadastrar com o campo endereço vazio!");
            return null;
        }

        if (cliente.getSenhaCliente().trim().equals("")) {
            System.err.println("Não e possivel cadastrar com o campo senha vazio!");
            return null;
        }
        
        cliente.setNumeroCelularCliente(formatarNumeroCelular(cliente.getNumeroCelularCliente()));

        cliente.setSaldo(100);

        cliente.setSenhaCliente(passwordSecurity.encriptarSenha(cliente.getSenhaCliente()));
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

    // Adicionar o método para validar CPF
    private boolean validarCPF(Cliente cliente) {
        CPF cpfUtil = new CPF(cliente.getCpfCliente());

        if (!cpfUtil.isCPF()) {
            return false;
        }

        cliente.setCpfCliente(cpfUtil.getCPF(true));
        return true;
    }


    public boolean checkSenha(Cliente cliente, String senha) {

        return passwordSecurity.checkSenha(senha, cliente.getSenhaCliente());

    }

    public boolean validarEmail(String email) {
        // Define a expressão regular para validar o formato do e-mail
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compila a expressão regular
        Pattern pattern = Pattern.compile(regex);

        // Cria um objeto Matcher para o e-mail fornecido
        Matcher matcher = pattern.matcher(email);

        // Verifica se o e-mail corresponde ao padrão definido
        return matcher.matches();
    }
    
 // Método para formatar o número de celular
    private String formatarNumeroCelular(String numeroCelular) {
        // Remove todos os caracteres não numéricos
        String numeroApenasDigitos = numeroCelular.replaceAll("\\D", "");

        // Verifica se o número tem 10 ou 11 dígitos
        if (numeroApenasDigitos.length() == 10 || numeroApenasDigitos.length() == 11) {
            return MessageFormat.format("({0}) {1}-{2}",
                    numeroApenasDigitos.substring(0, 2),
                    numeroApenasDigitos.substring(2, 7),
                    numeroApenasDigitos.substring(7));
        }

        return numeroCelular;
    }

    // Método para validar número de celular
    public boolean validarNumeroCelular(String numeroCelular) {
        // Formato aceito: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX ou XXXXXXXXXXX
        String regexNumeroCelular = "(\\(\\d{2}\\)\\d{4,5}-\\d{4})|\\d{11}|\\d{10}";

        Pattern pattern = Pattern.compile(regexNumeroCelular);
        Matcher matcher = pattern.matcher(numeroCelular);

        return matcher.matches();
    }

    // Método para validar data de nascimento
    public boolean validarDataNascimento(String dataNascimento) {
        // Formato aceito: DD/MM/YYYY
        String regexDataNascimento = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$";

        Pattern pattern = Pattern.compile(regexDataNascimento);
        Matcher matcher = pattern.matcher(dataNascimento);

        return matcher.matches();
    }

    public void atualizarClienteSaldo(Cliente cliente, double saldo)
    {
        Cliente c = consultarClientePorCpf(cliente.getCpfCliente());

        if (c == null) {
            System.err.println("Cliente inexistente para atualização de saldo");
            return;
        }

        c.setSaldo(saldo);
        clienteRepository.atualizarBaseDados();
    }
}


