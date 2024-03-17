package org.example;

import org.example.model.CategoriaEnum;
import org.example.model.Cliente;
import org.example.model.Endereco;
import org.example.security.PasswordSecurity;
import org.example.service.ClienteService;
import org.example.util.Constantes;
import org.example.util.FuncoesUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        aberturaBanco();

        while (true) {
            System.out.println("\nMENU INICIAL");
            System.out.println("(1) Cadastrar Novo Cliente");
            System.out.println("(2) Já sou cliente");
            System.out.println("(3) Sair");

            String escolhaUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolhaUsuario)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int escolha = Integer.parseInt(escolhaUsuario);

            switch (escolha) {
                case 1:
                    cadastrarNovoCliente(input);
                    break;
                case 2:
                    menuCartao(input);
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
            }
        }
    }

    private static void cadastrarNovoCliente(Scanner input) {
        Cliente novoCliente = new Cliente();

        novoCliente.setCpfCliente(validarEntradaPreenchida(input,
                "Digite o CPF (ex:000.000.000-00)",
                "CPF não preenchido"));

        novoCliente.setNomeCliente(validarEntradaPreenchida(input,
                "Digite o Nome Completo",
                "Nome não preenchido"));

        preencherDataNascimento(input, novoCliente);
        preencherEndereco(input, novoCliente);
        escolherCategoria(input, novoCliente);
        preencherSenha(input, novoCliente);
    }

    private static void preencherDataNascimento(Scanner input, Cliente novoCliente) {
        while (true) {
            System.out.println("Digite a Data de Nascimento (ex:dd/MM/yyyy)");
            String dtNasc = input.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {
                LocalDate parse = LocalDate.parse(dtNasc, formatter);

                LocalDate now = LocalDate.now().minusYears(18);

                if (parse.isAfter(now)) {
                    System.err.println("Data invalida, precisa ser maior de 18 anos");
                    continue;
                }

                novoCliente.setDataNascimentoCliente(parse);
            } catch (DateTimeParseException e) {
                System.err.println("Formato de data informado inválido, tente novamente");
                continue;
            }

            break;
        }
    }

    private static void preencherSenha(Scanner input, Cliente novoCliente) {
        while (true) {
            String senha1 = validarEntradaPreenchida(input,
                    "Digite a senha",
                    "Senha não preenchida");

            String senha2 = validarEntradaPreenchida(input,
                    "Digite novamente a Senha para confirmação",
                    "Senha não preenchida");

            if (!senha1.equals(senha2))
                continue;

            novoCliente.setSenhaCliente(PasswordSecurity.encriptarSenha(senha1));
            break;
        }
    }

    private static void preencherEndereco(Scanner input, Cliente novoCliente) {
        System.out.println("* ENDEREÇO *\n");

        Endereco enderecoCliente = new Endereco();

        enderecoCliente.setRua(validarEntradaPreenchida(input,
                "Digite a Rua",
                "Rua não preenchida"));

        enderecoCliente.setNumero(validarEntradaPreenchida(input,
                "Digite o Número",
                "Numero não preenchido"));

        System.out.println("Digite o complemento (Opcional)");
        enderecoCliente.setComplemento(input.nextLine());

        enderecoCliente.setBairro(validarEntradaPreenchida(input,
                "Digite o Bairro",
                "Bairro não preenchido"));

        enderecoCliente.setCidade(validarEntradaPreenchida(input,
                "Digite a Cidade",
                "Cidade não preenchida"));

        enderecoCliente.setEstado(validarEntradaPreenchida(input,
                "Digite o Estado em UF (ex:SP)",
                "UF não preenchido"));

        enderecoCliente.setCep(validarEntradaPreenchida(input,
                "Digite o CEP (ex:00000-000)",
                "CEP não preenchido"));

        novoCliente.setEndereco(enderecoCliente);
    }

    private static void escolherCategoria(Scanner input, Cliente novoCliente) {
        //TODO CATEGORIA
        while (true) {
            System.out.println("Escolha a Categoria");
            System.out.println("(1) Comum");
            System.out.println("(2) Super");
            System.out.println("(3) Premium");
            String escolhaCategoriaStr = input.nextLine();

            if (!FuncoesUtil.ehNumero(escolhaCategoriaStr)) {
                System.err.println("Digite somente numeros");
                continue;
            }

            CategoriaEnum categoria;

            switch (Integer.parseInt(escolhaCategoriaStr)) {
                case 1:
                    categoria = CategoriaEnum.COMUM;
                    break;
                case 2:
                    categoria = CategoriaEnum.SUPER;
                    break;
                case 3:
                    categoria = CategoriaEnum.PREMIUM;
                    break;
                default:
                    System.err.println("Escolha invalida");
                    continue;
            }

            novoCliente.setCategoria(categoria);
            break;
        }
    }

    private static String validarEntradaPreenchida(Scanner input,
                                                   String mensagemDeEntrada,
                                                   String mensagemDeErro) {
        while (true) {
            System.out.println(mensagemDeEntrada);
            String valor = input.nextLine();
            if (valor.isBlank()) {
                System.err.println(mensagemDeErro);
                continue;
            }
            return valor;
        }
    }

    //TODO CRIAR LOGAR CLIENTE
    public static void aberturaBanco() {
        System.out.println("   d888888o.   8888888 8888888888         .8.          8 888888888o.  ");
        System.out.println(" .`8888:' `88.       8 8888              .888.         8 8888    `88. ");
        System.out.println(" 8.`8888.   Y8       8 8888             :88888.        8 8888     `88  ");
        System.out.println(" `8.`8888.           8 8888            . `88888.       8 8888     ,88 ");
        System.out.println("  `8.`8888.          8 8888           .8. `88888.      8 8888.   ,88' ");
        System.out.println("   `8.`8888.         8 8888          .8`8. `88888.     8 888888888P'  ");
        System.out.println("    `8.`8888.        8 8888         .8' `8. `88888.    8 8888`8b      ");
        System.out.println("8b   `8.`8888.       8 8888        .8'   `8. `88888.   8 8888 `8b.    ");
        System.out.println("`8b.  ;8.`8888       8 8888       .888888888. `88888.  8 8888   `8b.  ");
        System.out.println(" `Y8888P ,88P'       8 8888      .8'       `8. `88888. 8 8888     `88.");
        System.out.println(" 8 888888888o            .8.          b.             8 8 8888     ,88'  ");
        System.out.println(" 8 8888    `88.         .888.         888o.          8 8 8888    ,88'   ");
        System.out.println(" 8 8888     `88        :88888.        Y88888o.       8 8 8888   ,88'    ");
        System.out.println(" 8 8888     ,88       . `88888.       .`Y888888o.    8 8 8888  ,88'     ");
        System.out.println(" 8 8888.   ,88'      .8. `88888.      8o. `Y888888o. 8 8 8888 ,88'      ");
        System.out.println(" 8 8888888888       .8`8. `88888.     8`Y8o. `Y88888o8 8 8888 88'       ");
        System.out.println(" 8 8888    `88.    .8' `8. `88888.    8   `Y8o. `Y8888 8 888888<        ");
        System.out.println(" 8 8888      88   .8'   `8. `88888.   8      `Y8o. `Y8 8 8888 `Y8.      ");
        System.out.println(" 8 8888    ,88'  .888888888. `88888.  8         `Y8o.` 8 8888   `Y8.    ");
        System.out.println(" 8 888888888P   .8'       `8. `88888. 8            `Yo 8 8888     `Y8.  ");
    }


    //menu para controle de cartao
    public static void menuCartao(Scanner input) {
        while (true) {
            System.out.println("(1) Cartão de Debito");
            System.out.println("(2) Cartão de Crédito");
            System.out.println("(9) Voltar Para o Menu Anterior");

            String escolherCartaoMenu = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolherCartaoMenu)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int escolherCartaoMenuInt = Integer.parseInt(escolherCartaoMenu);

            switch (escolherCartaoMenuInt) {
                //cartao de debito
                case 1:
                    menuCartaoDebito(input);
                    break;
                //cartao de credito
                case 2:
                    menuCartaoCredito(input);
                    break;
                //Voltar menu
                case 9:
                    return;

            }
        }

    }

    public static void menuCartaoDebito(Scanner input) {
        while (true) {
            System.out.println("(1) Adquirir Cartão");
            System.out.println("(2) Alterar Limite Diário");
            System.out.println("(3) Cancelar Cartão");
            System.out.println("(9) Voltar Para o Menu Anterior");

            String escolherDebitoMenu = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolherDebitoMenu)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int escolherDebitoMenuInt = Integer.parseInt(escolherDebitoMenu);

            switch (escolherDebitoMenuInt) {
                //adquirir cartao
                case 1:
                    break;
                //Alterar limite diario
                case 2:
                    break;
                //cancelar cartao
                case 3:
                    break;
                //Voltar menu
                case 9:
                    return;

            }
        }
    }

    public static void menuCartaoCredito(Scanner input) {
        while (true) {
            System.out.println("(1) Adquirir Cartão");
            System.out.println("(2) Alterar Limite");
            System.out.println("(3) Seguro Cartão");
            System.out.println("(4) Cancelar Cartão");
            System.out.println("(9) Voltar Para o Menu Anterior");

            String escolherCreditoMenu = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolherCreditoMenu)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int escolherCreditoMenuInt = Integer.parseInt(escolherCreditoMenu);

            switch (escolherCreditoMenuInt) {
                //adquirir cartao
                case 1:
                    break;
                //alterar limite
                case 2:
                    break;
                //menu de seguro do cartao
                case 3:
                    menuSeguro(input);
                    break;
                //cancelar cartao
                case 4:
                    break;
                //Voltar menu
                case 9:
                    return;

            }
        }
    }

    public static void menuSeguro(Scanner input) {
        while (true) {
            System.out.println("(1) Adquirir Seguro");
            System.out.println("(2) Consultar Seguros Adquiridos ");
            System.out.println("(3) Cancelar Seguro ");
            System.out.println("(9) Voltar Para o Menu Anterior");

            String escolherSeguroMenu = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolherSeguroMenu)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int escolherSeguroMenuInt = Integer.parseInt(escolherSeguroMenu);

            switch (escolherSeguroMenuInt) {
                //adquirir seguro
                case 1:
                    break;
                //consultar seguro
                case 2:
                    break;
                //cancelar seguro
                case 3:
                    break;
                //Voltar menu
                case 9:
                    return;

            }

        }
    }

}

