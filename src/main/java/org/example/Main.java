package org.example;

import org.example.model.*;
import org.example.repository.ClienteRepository;
import org.example.service.CartaoService;
import org.example.service.ClienteService;
import org.example.service.ContaService;
import org.example.service.TransferenciaService;
import org.example.util.FuncoesUtil;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static ClienteRepository clienteRepository = new ClienteRepository();
    static ClienteService clienteService = new ClienteService(clienteRepository);
    static TransferenciaService transferenciaService = new TransferenciaService(clienteRepository);
    static ContaService contaService = new ContaService();
    static CartaoService cartaoService = new CartaoService();

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
                    fazerLogin(input);
                    break;
                case 3:
                    return;
                default:
                    System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
            }
        }
    }

    private static void fazerLogin(Scanner input) {

        System.out.println("--- LOGIN ---\n");
        System.out.println("Digite o CPF");
        String loginCpf = input.nextLine();

        System.out.println("Digite a Senha");
        String loginSenha = input.nextLine();
        Cliente cliente = clienteService.logarCliente(loginCpf, loginSenha);

        if (cliente == null) {
            System.err.println("Erro! Senha ou CPF está inválido!");
            return;
        }
        usuarioLogado(input, cliente);
    }

    public static void usuarioLogado(Scanner input, Cliente cliente) {
        while (true) {
            System.out.println("\n=== " + cliente.getNomeCliente() + " ===\n");

            mostrarDadosConta(cliente.getContaCorrente());
            mostrarDadosConta(cliente.getContaPoupanca());

            System.out.println("(1) Trasferência");
            System.out.println("(2) Perfil");
            System.out.println("(3) Cartão");
            System.out.println("(4) Conta");
            System.out.println("(9) Sair");
            System.out.print("Escolha a opção desejada: ");

            String logadoUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(logadoUsuario)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int Usuario = Integer.parseInt(logadoUsuario);

            switch (Usuario) {
                case 1:
                    operacaoBancaria(input, cliente);
                    break;
                case 2:
                    meuPerfil(input, cliente);
                    break;
                case 3:
                    menuCartao(input, cliente);
                    break;
                case 4:
                    menuContas(input, cliente);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    private static void mostrarDadosConta(Conta conta) {
        System.out.println(conta.getTipoConta());
        System.out.println("AGENCIA: " + conta.getNumeroAgencia());
        System.out.println("CONTA: " + conta.getNumeroConta() + " DIGITO: " + conta.getDigitoConta());
        System.out.println("SALDO: " + conta.getSaldo());
        System.out.println();
    }

    private static void menuContas(Scanner input, Cliente cliente) {
        //todo implementar menu de acesso para cada tipo de conta
        // conta corrente mostrar a opção de saldo e o custo atual da taxa de manutenção
        // conta poupança mostrar a opção de saldo e o rendimento atual da conta {Patrick}


        while (true) {
            System.out.println("(1) Conta Corrente");
            System.out.println("(2) Conta Poupança");
            System.out.println("(9) Voltar para o menu anterior");

            String escolhaConta = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolhaConta)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int escolhaContaInt = Integer.parseInt(escolhaConta);

            switch (escolhaContaInt) {
                case 1:

                    contaCorrente(input, cliente);
                    break;
                case 2:
                    contaPoupanca(input, cliente);

                    break;
                case 9:
                    return;
            }


        }

    }

    private static void contaPoupanca(Scanner input, Cliente cliente) {
        //TODO preciso fazer um sistema para calcular o rendimento por dia
        // e entregar uma previsao do rendimento no futuro

        System.out.println("--- Conta Poupança ---\n");
        System.out.print("Saldo Atual: ");
        System.out.printf("R$ %.2f%", cliente.getContaPoupanca().getSaldo());
        System.out.println("\n Categoria do cliente: " + cliente.getCategoria());
        System.out.println("Taxa de manutenção: " + cliente.getContaPoupanca().getTaxaRendimento());

        LocalDate now = LocalDate.now();
        LocalDate endMonth = now.withDayOfMonth(now.lengthOfMonth());
        long daysToEndMonth = now.until(endMonth, ChronoUnit.DAYS);
        int daysToEndMonthInt = (int) daysToEndMonth;

        double saldoCliente = cliente.getContaPoupanca().getSaldo();
        double rendimentoMensal = (saldoCliente * cliente.getContaPoupanca().getTaxaRendimento());
        //double rendimentoDiario = rendimentoMensal / daysToEndMonthInt;


        if (now.equals(endMonth)) {
            cliente.getContaPoupanca().setSaldo(saldoCliente += rendimentoMensal);
            endMonth = endMonth.plusMonths(1).withDayOfMonth(endMonth.lengthOfMonth());
        }
        while (true) {
            System.out.println("(1) Cálculo de rendimento da poupança");
            System.out.println("(9) Voltar para o menu anterior");

            String escolhaPoupanca = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolhaPoupanca)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int escolhaPoupancaInt = Integer.parseInt(escolhaPoupanca);

            switch (escolhaPoupancaInt) {
                case 1:
                    System.out.println("Digite quantos dias você deseja deixar o dinheiro rendendo na poupança");
                    int days = input.nextInt();
                    double rendimentoDiariaPrevisao = rendimentoFuturo(saldoCliente, rendimentoMensal, days) / days;
                    System.out.println("Após " + days + "sua conta terá rendido " + rendimentoDiariaPrevisao + "por dia");
                    System.out.println("Totalizando "+(rendimentoFuturo(saldoCliente, rendimentoMensal, days)+saldoCliente));
                    break;
                case 9:
                    return;

            }
        }


    }

    private static double rendimentoFuturo(double saldoCliente, double rendimentoMensal, int days) {

        LocalDate now = LocalDate.now();
        LocalDate userDaySelect = now.plusDays(days);
        LocalDate endMonth = now.withDayOfMonth(now.lengthOfMonth());
        double saldoInicial = saldoCliente;


        while (now.isBefore(userDaySelect)) {


            if (now.equals(endMonth)) {

                saldoCliente += rendimentoMensal;

                endMonth = endMonth.plusMonths(1).withDayOfMonth(endMonth.lengthOfMonth());

            }
            now = now.plusDays(1);
        }

        double rendimentoFinal = saldoCliente + rendimentoMensal;

        return rendimentoFinal - saldoInicial;

    }


    private static void contaCorrente(Scanner input, Cliente cliente) {
        System.out.print("Saldo Atual: ");
        System.out.printf("R$ %.2f%", (cliente.getContaCorrente().getSaldo()));
        System.out.println("\n Categoria do cliente: " + cliente.getCategoria());
        System.out.println("Taxa de manutenção: " + cliente.getContaCorrente().getTaxaManutencao());
    }


    private static void cadastrarNovoCliente(Scanner input) {
        Cliente novoCliente = new Cliente();

        do {
            String cpfCliente = validarEntradaPreenchida(input,
                    "Digite o CPF (ex:000.000.000-00)",
                    "CPF não preechido");

            if (!FuncoesUtil.validarFormatoCpf(cpfCliente)) {
                System.err.println("Erro! O CPF deve estar com formatação correta.");
                continue;
            }
            if (!FuncoesUtil.validarCPF(cpfCliente)) {
                System.err.println("CPF inválido! O CPF deve ser valido!");
                continue;
            }
            novoCliente.setCpfCliente(cpfCliente);
            break;
        } while (true);


        do {
            String nomeCliente = validarEntradaPreenchida(input,
                    "Digite o Nome Completo",
                    "Nome não preenchido");
            if (!FuncoesUtil.validarNomeCliente(nomeCliente)) {
                System.err.println("Nome inválido! O nome deve conter somente letras, com no mínimo 2 e no máximo 100 caracteres.");
                continue;
            }
            novoCliente.setNomeCliente(nomeCliente);
            break;
        } while (true);


        preencherDataNascimento(input, novoCliente);
        preencherEndereco(input, novoCliente);
        preencherSenha(input, novoCliente);
        escolherCategoria(input, novoCliente);
        criarConta(input, novoCliente);

        if(clienteService.clienteNovo(novoCliente) == null){
            System.err.println("Erro! Cadastro não realizado");
            return;
        }

        System.out.println("Cadastro realizado com sucesso!");
        System.out.println("Utilize o CPF e senha definida no cadastro para logar na conta");
    }

    private static void criarConta(Scanner input, Cliente novoCliente) {

        while (true) {
            System.out.println("- MENU CRIAR CONTA -");
            System.out.println("(1) Criar Conta Corrente");
            System.out.println("(2) Criar Conta Poupança");
            System.out.println("(3) Criar Conta Corrente e Conta Poupança");
            String escolhaConta = input.nextLine();

            if (!FuncoesUtil.ehNumero(escolhaConta)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int escolhaContaCliente = Integer.parseInt(escolhaConta);

            switch (escolhaContaCliente) {
                case 1:
                    criarContaCorrente(novoCliente, input);
                    return;
                case 2:
                    criarContaPoupanca(novoCliente, input);
                    return;
                case 3:
                    criarContaCorrente(novoCliente, input);
                    criarContaPoupanca(novoCliente, input);
                    return;
                default:
                    System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
            }
        }
    }

    private static void criarContaPoupanca(Cliente novoCliente, Scanner input) {

        ContaPoupanca criarContaPoupanca = new ContaPoupanca();
        Random gerarNumeroConta = new Random();

        criarContaPoupanca.setTipoConta(TipoConta.POUPANCA);

        criarContaPoupanca.setNumeroAgencia("0001");
        System.out.println("AGENCIA: " + criarContaPoupanca.getNumeroAgencia());

        criarContaPoupanca.setNumeroConta(String.valueOf(gerarNumeroConta.nextInt(9999) + 10000));
        System.out.println("CONTA: " + criarContaPoupanca.getNumeroConta());
        criarContaPoupanca.setDigitoConta("1");

        double taxaMensal = 0;

        switch (novoCliente.getCategoria()) {
            case SUPER:
                taxaMensal = 0.007;
                break;
            case COMUM:
                taxaMensal = 0.005;
                break;
            case PREMIUM:
                taxaMensal = 0.009;
                break;
            default:
                System.err.println("Erro, cliente sem categoria!");
                return;
        }
        System.out.println("RENDIMENTO MENSAL: " + String.format("%.2f", taxaMensal * 100) + "%");

        criarContaPoupanca.setTaxaRendimento(taxaMensal);
        criarContaPoupanca.setSaldo(0.00);
        System.out.println("SALDO: " + criarContaPoupanca.getSaldo());

        novoCliente.setContaPoupanca(criarContaPoupanca);
    }


    private static void criarContaCorrente(Cliente novoCliente, Scanner input) {

        ContaCorrente criarContaCorrente = new ContaCorrente();
        Random gerarNumeroConta = new Random();

        criarContaCorrente.setTipoConta(TipoConta.CORRENTE);

        criarContaCorrente.setNumeroAgencia("0001");
        System.out.println("AGENCIA: " + criarContaCorrente.getNumeroAgencia());

        criarContaCorrente.setNumeroConta(String.valueOf(gerarNumeroConta.nextInt(9999) + 10000));
        System.out.println("CONTA: " + criarContaCorrente.getNumeroConta());
        criarContaCorrente.setDigitoConta("2");

        double taxaManutencao = 0;

        switch (novoCliente.getCategoria()) {
            case SUPER:
                taxaManutencao = 12.00;
                break;
            case COMUM:
                taxaManutencao = 8.00;
                break;
            case PREMIUM:
                taxaManutencao = 0;
                break;
            default:
                System.err.println("Erro, cliente sem categoria!");
                return;
        }
        System.out.println("TAXA DE RENDIMENTO MENSAL: " + String.format("%.2f", taxaManutencao));

        criarContaCorrente.setTaxaManutencao(taxaManutencao);
        criarContaCorrente.setSaldo(0.00);
        System.out.println("SALDO: " + criarContaCorrente.getSaldo());

        novoCliente.setContaCorrente(criarContaCorrente);
    }

    private static void preencherDataNascimento(Scanner input, Cliente novoCliente) {
        while (true) {
            //TODO COLOCAR IDADE LIMITE (100 ANOS) PARA REALIZAR CADASTRO CONTA {Willians}
            System.out.println("Digite a Data de Nascimento (ex:dd/MM/yyyy)");
            String dtNasc = input.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {
                LocalDate parse = LocalDate.parse(dtNasc, formatter);

                LocalDate now = LocalDate.now().minusYears(18);

                if (parse.isAfter(now)) {
                    System.err.println("Erro, precisa ser maior de 18 anos");
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

            String senha1;
            do {
                senha1 = validarEntradaPreenchida(input,
                        "Crie uma senha com 4 números (ex:0000)",
                        "Senha não preenchida");
                if (!FuncoesUtil.validarSenha(senha1)) {
                    System.err.println("Senha invalida! Crie uma senha de 4 digitos.");
                   continue;
                }
                novoCliente.setSenhaCliente(senha1);
                break;

            } while (true);

            do {
                String senha2 = validarEntradaPreenchida(input,
                        "Digite novamente a Senha para confirmação",
                        "Senha não preenchida");

                if (!senha1.equals(senha2)) {
                    System.err.println("Senha incorreta! Verifique a senha digitada.");
                    continue;
                }
                novoCliente.setSenhaCliente(senha1);
                break;

            } while (true);
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

        do {
           String uf = validarEntradaPreenchida(input,
                    "Digite o Estado em UF (ex:SP)",
                    "UF não preenchido");
            if (!FuncoesUtil.validarUF(uf)) {
                System.err.println("UF inválido! UF deve conter somente letras maiuscuslas, com no máximo 2 letras (ex:SP)");
            }
            enderecoCliente.setEstado(uf);
            break;
        } while (true);

        do {
            String cep = validarEntradaPreenchida(input,
                    "Digite o CEP (ex:00000-000)",
                    "CEP não preenchido");
            if (!FuncoesUtil.validarCEP(cep)) {
                System.err.println("CEP inválido! CEP deve contar a pontuação (ex:00000-000).");
            }
            enderecoCliente.setCep(cep);
            break;
        } while (true);

    }

    private static void escolherCategoria(Scanner input, Cliente novoCliente) {
        while (true) {
            System.out.println("\nEscolha a Categoria");
            System.out.println("(1) Comum");
            System.out.println("CONTA CORRENTE:Taxa de Manutenção Mensal: R$ 12,00 ");
            System.out.println("CONTA POUPANÇA:Taxa de Rendimento Mensal: 0,5 %");

            System.out.println("\n(2) Super");
            System.out.println("CONTA CORRENTE: Taxa de Manutenção Mensal: R$ 8,00 ");
            System.out.println("CONTA POUPANÇA: Taxa de Rendimento Mensal: 0,7 %");

            System.out.println("\n(3) Premium");
            System.out.println("CONTA CORRENTE: Taxa de Manutenção Mensal: ISENTO");
            System.out.println("CONTA POUPANÇA: Taxa de Rendimento Mensal: 0,9 %");
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

    public static void operacaoBancaria(Scanner input, Cliente cliente) {
        //todo implementar transferencias {Raphaela}
        while (true) {
            System.out.println("\n=== TRANSFERÊNCIAS ===");
            System.out.println("(1) Transferência entre contas starbank");
            System.out.println("(2) PIX");
            System.out.println("(3) Consultar Extrato");
            System.out.println("(9) Voltar para o menu anterior");
            System.out.print("Escolha a opção desejada: ");

            String transferenciaUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(transferenciaUsuario)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int transferencia = Integer.parseInt(transferenciaUsuario);

            switch (transferencia) {
                case 1:
                    System.out.println("== Transferência entre contas starbank ==");
                    // transferência dentro do mesmo banco
                    break;
                case 2:
                    menuPIX(input);
                    break;
                case 3:
                    System.out.println("== Consultar Extrato ==");
                    //  consultar extrato
                    break;
                case 9:
                    System.out.println("Voltando ao Menu.");
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    public static void menuPIX(Scanner input) {

        while (true) {
            //todo a chave do pix sempre vai ser o cpf do cliente {Raphaela}
            System.out.println("\n=== PIX ===");
            System.out.println("(1) Enviar PIX");
            System.out.println("(2) Receber PIX");
            System.out.println("(9) Voltar para menu anterior");
            System.out.print("Escolha a opção desejada: ");

            String transferenciaPix = input.nextLine();
            if (!FuncoesUtil.ehNumero(transferenciaPix)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int pix = Integer.parseInt(transferenciaPix);

            switch (pix) {
                case 1:
                    System.out.println("== Enviar PIX ==");
                    //  enviar PIX
                    break;
                case 2:
                    System.out.println("== Receber PIX ==");
                    //  receber PIX
                    break;
                case 9:
                    // usuarioLogado(input);
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    public static void meuPerfil(Scanner input, Cliente cliente) {
        while (true) {
            System.out.println("\n=== MEU PERFIL ===");
            System.out.println("(1) Atualização Cadastral ");
            System.out.println("(2) Alterar Categoria");
            System.out.println("(9) Voltar para o menu anterior");
            System.out.print("Escolha a opção desejada: ");

            String PerfilUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(PerfilUsuario)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int perfil = Integer.parseInt(PerfilUsuario);

            switch (perfil) {
                case 1:
                    alterarDados(input, cliente);
                    break;
                case 2:
                    alterarCategoriaConta(input, cliente);
                    break;
                case 9:
                    System.out.println("Voltando ao Menu.");
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    private static void alterarCategoriaConta(Scanner input, Cliente cliente) {
        //todo implementar troca de categoria do cliente e troca das taxas de rendimentos e manutenção {Raphaela}
        escolherCategoria(input, cliente);
    }

    public static void alterarDados(Scanner input, Cliente cliente) {

        while (true) {
            System.out.println("\n== Perfil ==");
            System.out.println("(1) Nome de preferência");
            System.out.println("(2) Data de nascimento");
            System.out.println("(3) Senha");
            System.out.println("(4) Endereço");
            System.out.println("(9) Voltar para o menu anterior");
            System.out.print("Escolha a opção desejada: ");

            String AlterarUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(AlterarUsuario)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int alterar = Integer.parseInt(AlterarUsuario);

            //TODO implementar alteracao dos dados cadastrais do cliente {Willians}

            switch (alterar) {
                case 1:
                    System.out.println("== Nome ==");
                    //  nome
                    break;
                case 2:
                    System.out.println("== Data de nascimento ==");
                    //  data nascimento
                    break;
                case 3:
                    //todo alterar senha {Patrick}
                    break;
                case 4:
                    System.out.println("Endereço.");
                    // endereço
                    break;
                case 9:
                    //   usuarioLogado(input);
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    //menu para controle de cartao
    public static void menuCartao(Scanner input, Cliente cliente) {
        while (true) {

            System.out.println("\n== Meu Cartão ==");
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
            System.out.println("\n== Cartão de Debito ==");
            System.out.println("(1) Adquirir Cartão");
            System.out.println("(2) Alterar Limite Diário");
            System.out.println("(3) Cancelar Cartão");
            System.out.println("(9) Voltar Para o Menu Anterior");

            //todo listar todos os cartoes de debito(deve se listar todos os cartoes com seus status(ATIVADO, DESATIVADO))
            // criar funcionalidades de adquirir, alterar limite e cancelar cartão(no cancelar cartao, deve ser alterar o status para cancelado na enum dele) {Patrick}

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
            //todo para todas as operções exceto adquirir cartao, deve ser identificado
            // o cartao que o usuario deseja fazer as operações {Patrick}
            System.out.println("\n== Cartão de Crédito ==");
            System.out.println("(1) Adquirir Cartão");
            System.out.println("(2) Alterar Limite");
            System.out.println("(3) Seguro Cartão");
            System.out.println("(4) Cancelar Cartão");
            System.out.println("(9) Voltar Para o Menu Anterior");

            //todo listar todos os cartoes de credito {Patrick}

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
            System.out.println("\n== Meu Seguro ==");
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
}

