package org.example;

import org.example.model.*;
import org.example.repository.ClienteRepository;
import org.example.service.*;
import org.example.util.FuncoesUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static ClienteRepository clienteRepository = new ClienteRepository();
    static ClienteService clienteService = new ClienteService(clienteRepository);
    static TransferenciaService transferenciaService = new TransferenciaService(clienteService);
    static ContaService contaService = new ContaService(clienteService);
    static CartaoService cartaoService = new CartaoService(clienteService);

    static SeguroService seguroService = new SeguroService(clienteService);

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
    //todo alterar pastas conforme pedido do bruno {Raphaela}
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
            System.out.println("\n=== CLIENTE " + cliente.getCategoria() + " ===");
            System.out.println("=== BEM VINDO " + cliente.getNomeCliente() + " ===\n");

            if (cliente.getContaCorrente() != null) {
                mostrarDadosConta(cliente.getContaCorrente());
                System.out.printf("TAXA DE MANUNTENÇÃO: R$ %.2f\n\n", cliente.getContaCorrente().getTaxaManutencao());
            }

            if (cliente.getContaPoupanca() != null) {
                mostrarDadosConta(cliente.getContaPoupanca());
                System.out.printf("TAXA DE RENDIMENTO: %.2f%% \n\n", cliente.getContaPoupanca().getTaxaRendimento() * 100);
            }

            System.out.println("(1) Trasferência");
            System.out.println("(2) Extrato");
            System.out.println("(3) Perfil");
            System.out.println("(4) Cartão");
            System.out.println("(5) Conta");
            System.out.println("(9) Sair");
            System.out.println("Escolha a opção desejada: ");

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
                    mostrarExtrato(cliente);
                    break;
                case 3:
                    meuPerfil(input, cliente);
                    break;
                case 4:
                    menuEscolhaContaCartao(input, cliente);
                    break;
                case 5:
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
        System.out.println("TIPO DE CONTA:" + conta.getTipoConta());
        System.out.println("AGENCIA: " + conta.getNumeroAgencia());
        System.out.println("CONTA: " + conta.getNumeroConta() + " DIGITO: " + conta.getDigitoConta());
        System.out.println("SALDO: " + conta.getSaldo());
    }

    private static void menuContas(Scanner input, Cliente cliente) {

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
        if (cliente.getContaPoupanca() == null) {
            System.err.println("O cliente não possui conta poupança");
            return;
        }

        while (true) {
            System.out.println("--- Conta Poupança ---\n");
            System.out.println("Saldo Atual: ");
            System.out.printf("R$ %.2f", cliente.getContaPoupanca().getSaldo());
            System.out.println("\nCategoria do cliente: " + cliente.getCategoria());
            System.out.println("Taxa de rendimento: " + cliente.getContaPoupanca().getTaxaRendimento());

            System.out.println("(1) Cálcular rendimento da poupança");
            System.out.println("(2) Calcular provisão da poupança até o fim do mês");
            System.out.println("(3) Cálcular provisão de rendimento da poupança por uma determinada quantidade de dias");
            System.out.println("(9) Voltar para o menu anterior");

            String escolhaPoupanca = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolhaPoupanca)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }
            int escolhaPoupancaInt = Integer.parseInt(escolhaPoupanca);

            double rendimento = 0;

            switch (escolhaPoupancaInt) {
                case 1:
                    rendimento = contaService.calcularRendimentoPoupanca(cliente);
                    System.out.printf("O rendimento atual é R$ %.2f\n", rendimento);
                    break;
                case 2:
                    rendimento = contaService.calcularRendimentoFuturoAteOFinalDoMes(cliente);
                    System.out.printf("O rendimento até o final do mês será de R$ %.2f\n", rendimento);
                    break;
                case 3:
                    while (true) {
                        String qtdDias = validarEntradaPreenchida(input,
                                "Digite quantos dias você deseja deixar o dinheiro rendendo na poupança",
                                "Preencha com numeros a quantidade de dias que deseja simular");

                        if (!FuncoesUtil.ehNumero(qtdDias)) {
                            System.err.println("Digite apenas numeros");
                            continue;
                        }

                        int days = Integer.parseInt(qtdDias);
                        double rendimentoDiariaPrevisao = contaService.calcularRendimentoPoupancaProvisaoDias(cliente, days);
                        System.out.printf("Após " + days + " dias sua conta terá rendido %.2f\n", rendimentoDiariaPrevisao);
                        System.out.printf("Totalizando %.2f\n", cliente.getContaPoupanca().getSaldo() + rendimentoDiariaPrevisao);
                        break;
                    }
                    break;
                case 9:
                    return;
            }
        }


    }


    private static void contaCorrente(Scanner input, Cliente cliente) {
        if (cliente.getContaCorrente() == null) {
            System.err.println("O cliente não possui conta corrente");
            return;
        }

        while (true) {
            System.out.println("Saldo Atual: ");
            System.out.printf("R$ %.2f", (cliente.getContaCorrente().getSaldo()));
            System.out.println("\nCategoria do cliente: " + cliente.getCategoria());
            System.out.println("Taxa de manutenção: " + cliente.getContaCorrente().getTaxaManutencao());

            System.out.println("(1) - Calcular Custo de Manutenção");
            System.out.println("(9) - Sair");

            String escolhaConta = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolhaConta)) {
                System.err.println("Opção inválida, utilize somente os número mostrados no Menu!");
                continue;
            }

            int escolhaContaInt = Integer.parseInt(escolhaConta);

            switch (escolhaContaInt) {
                case 1:
                    double custoManutencao = contaService.calcularCustoContaCorrente(cliente);
                    System.out.printf("Custo de manutenção até a data atual é de: %.2f\n", custoManutencao);
                    System.out.printf("O custo de manutenção mensal é: %.2f\n", cliente.getContaCorrente().getTaxaManutencao());
                    break;
                case 9:
                    return;
            }

        }

    }


    private static void cadastrarNovoCliente(Scanner input) {
        Cliente novoCliente = new Cliente();

        do {
            String cpfCliente = validarEntradaPreenchida(input,
                    "Digite o CPF (ex:000.000.000-00) ou -1 para sair",
                    "CPF não preechido");

            if (cpfCliente.trim().equals("-1"))
                return;

            if (!FuncoesUtil.validarFormatoCpf(cpfCliente)) {
                System.err.println("Erro! O CPF deve estar com formatação correta.");
                continue;
            }
            if (!FuncoesUtil.validarCPF(cpfCliente)) {
                System.err.println("CPF inválido! O CPF deve ser valido!");
                continue;
            }

            if (clienteService.cpfJaCadastrado(cpfCliente)) {
                System.err.println("CPF já cadastrado");
                return;
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

        if (clienteService.clienteNovo(novoCliente) == null) {
            System.err.println("Erro! Cadastro não realizado");
            return;
        }

        System.out.println("\nCadastro realizado com sucesso!");
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

        System.out.println("\n------ CONTA POUPANÇA -----\n");

        System.out.println("NOME: " + novoCliente.getNomeCliente());

        criarContaPoupanca.setNumeroAgencia("0001");
        System.out.println("AGENCIA: " + criarContaPoupanca.getNumeroAgencia());

        criarContaPoupanca.setNumeroConta(String.valueOf(gerarNumeroConta.nextInt(9999) + 10000));

        System.out.println("CONTA: " + criarContaPoupanca.getNumeroConta());
        criarContaPoupanca.setDigitoConta("1");

        System.out.println("DIGITO: " + criarContaPoupanca.getDigitoConta());

        if (!definirRendimentoPoupanca(novoCliente, criarContaPoupanca))
            return;

        System.out.println("RENDIMENTO MENSAL: " + String.format("%.2f", criarContaPoupanca.getTaxaRendimento() * 100) + "%");

        criarContaPoupanca.setSaldo(0.00);
        System.out.println("SALDO: " + criarContaPoupanca.getSaldo());

        novoCliente.setContaPoupanca(criarContaPoupanca);

        System.out.println("Conta Poupança criada com sucesso!");
    }

    private static boolean definirRendimentoPoupanca(Cliente novoCliente, ContaPoupanca criarContaPoupanca) {
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
                return false;
        }
        criarContaPoupanca.setTaxaRendimento(taxaMensal);
        return true;
    }

    private static void criarContaCorrente(Cliente novoCliente, Scanner input) {

        ContaCorrente criarContaCorrente = new ContaCorrente();
        Random gerarNumeroConta = new Random();

        criarContaCorrente.setTipoConta(TipoConta.CORRENTE);
        System.out.println("\n------ CONTA CORRENTE -----\n");
        criarContaCorrente.setNumeroAgencia("0001");

        System.out.println("NOME: " + novoCliente.getNomeCliente());

        System.out.println("AGENCIA: " + criarContaCorrente.getNumeroAgencia());

        criarContaCorrente.setDigitoConta("2");
        System.out.println("DIGITO: " + criarContaCorrente.getDigitoConta());

        criarContaCorrente.setNumeroConta(String.valueOf(gerarNumeroConta.nextInt(9999) + 10000));
        System.out.println("CONTA: " + criarContaCorrente.getNumeroConta());


        if (!definirTaxaManutencao(novoCliente, criarContaCorrente))
            return;
        System.out.println("TAXA DE MANUTENÇÃO MENSAL: " + String.format("%.2f", criarContaCorrente.getTaxaManutencao()));

        criarContaCorrente.setSaldo(0.00);
        System.out.println("SALDO: " + criarContaCorrente.getSaldo());

        novoCliente.setContaCorrente(criarContaCorrente);
        System.out.println("Conta Corrente criada com sucesso!");
    }

    private static boolean definirTaxaManutencao(Cliente novoCliente, ContaCorrente criarContaCorrente) {
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
                return false;
        }

        criarContaCorrente.setTaxaManutencao(taxaManutencao);
        return true;
    }

    private static void preencherDataNascimento(Scanner input, Cliente novoCliente) {
        do {
            String dtNasc = validarEntradaPreenchida(input,
                    "Digite a Data de Nascimento (ex: dd/mm/yyyy)",
                    "Data de Nascimento não preenchida");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

            try {
                LocalDate parse = LocalDate.parse(dtNasc, formatter);

                LocalDate now = LocalDate.now().minusYears(18);
                if (parse.isAfter(now)) {
                    System.err.println("Erro! Você precisa ter mais de 18 anos para realizar o cadastro.");
                    continue;
                }

                now = LocalDate.now().minusYears(100);
                if (parse.isBefore(now)) {
                    System.err.println("Erro! Você não pode ter mais de 100 anos para realizar o cadastro.");
                    continue;
                }
                novoCliente.setDataNascimentoCliente(parse);
                break;

            } catch (Exception e) {
                System.err.println("Entrada de data invalida, tente novamente.");
            }

        } while (true);

    }

    private static void preencherSenha(Scanner input, Cliente novoCliente) {

        String senha1;
        do {
            senha1 = validarEntradaPreenchida(input,
                    "Crie uma senha com 4 números (ex:0000)",
                    "Senha não preenchida");
            if (!FuncoesUtil.validarSenha(senha1)) {
                System.err.println("Senha invalida! Crie uma senha numérica de 4 digitos.");
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
                continue;
            }
            enderecoCliente.setEstado(uf);
            break;
        } while (true);

        do {
            String cep = validarEntradaPreenchida(input,
                    "Digite o CEP (ex:00000-000)",
                    "CEP não preenchido");
            if (!FuncoesUtil.validarCEP(cep)) {
                System.err.println("CEP inválido! CEP deve contar so números e a pontuação (ex:00000-000).");
                continue;
            }
            enderecoCliente.setCep(cep);
            break;
        } while (true);

        novoCliente.setEndereco(enderecoCliente);
    }

    private static void escolherCategoria(Scanner input, Cliente novoCliente) {
        while (true) {
            System.out.println("\nEscolha a Categoria:");
            System.out.println("(1) COMUM");
            System.out.println("\tCONTA CORRENTE: Taxa de Manutenção Mensal: R$ 12,00 ");
            System.out.println("\tCONTA POUPANCA: Taxa de Rendimento Mensal: 0,5 %");

            System.out.println("\n(2) SUPER");
            System.out.println("\tCONTA CORRENTE: Taxa de Manutenção Mensal: R$ 8,00 ");
            System.out.println("\tCONTA POUPANCA: Taxa de Rendimento Mensal: 0,7 %");

            System.out.println("\n(3) PREMIUM");
            System.out.println("\tCONTA CORRENTE: Taxa de Manutenção Mensal: ISENTO");
            System.out.println("\tCONTA POUPANCA: Taxa de Rendimento Mensal: 0,9 %");
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
        while (true) {

            System.out.println("\n=== TRANSFERÊNCIAS ===");
            System.out.println("(1) PIX");
            System.out.println("(2) Deposito");
            System.out.println("(3) Consultar Extrato");
            System.out.println("(9) Voltar para o menu anterior");
            System.out.println("Escolha a opção desejada: ");

            String transferenciaUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(transferenciaUsuario)) {
                System.err.println("Entrada inválida, utilize somente os número");
                continue;
            }
            int transferencia = Integer.parseInt(transferenciaUsuario);

            switch (transferencia) {
                case 1:
                    menuPIX(input, cliente);
                    break;
                case 2:
                    criarDeposito(input, cliente);
                    break;
                case 3:
                    System.out.println("== Consultar Extrato ==");
                    mostrarExtrato(cliente);
                    break;
                case 9:
                    System.out.println("Voltando ao Menu.");
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    private static void criarDeposito(Scanner input, Cliente cliente) {
        while (true) {

            System.out.println("Digite o valor a ser depositado em sua conta");
            String valorDeposito = input.nextLine();

            if (!FuncoesUtil.ehNumero(valorDeposito)) {
                System.err.println("Digite um valor válido, lembre-se use ponto em vez de vírgula ex(1.50)");
                continue;
            }

            double valor = Double.parseDouble(valorDeposito);

            if (valor <= 0) {
                System.err.println("Valor nao pode ser negativo e nem zero");
                continue;
            }

            Conta conta = escolherContaDestino(input, cliente.getContaCorrente() != null, cliente.getContaPoupanca() != null, cliente);

            transferenciaService.depositar(cliente, conta, valor);

            System.out.println("Depósito concluido com sucesso");

            break;
        }
    }

    private static void mostrarExtrato(Cliente cliente) {

        if (cliente.getContaCorrente() != null) {
            if (cliente.getContaCorrente().getTransferencias().isEmpty()) {
                System.out.println("Não há transações na conta corrente");
                return;
            }
            System.out.println("-------- INICIO EXTRATO CONTA CORRENTE --------");
            mostrarExtratoConta(cliente.getContaCorrente());
            System.out.println("-------- FIM EXTRATO CONTA CORRENTE --------");
        }

        System.out.print("\n");

        if (cliente.getContaPoupanca() != null) {
            if (cliente.getContaPoupanca().getTransferencias().isEmpty()) {
                System.out.println("Não há transações na conta poupança");
                return;
            }

            System.out.println("-------- INICIO EXTRATO CONTA POUPANÇA --------");
            mostrarExtratoConta(cliente.getContaPoupanca());
            System.out.println("-------- FIM EXTRATO CONTA POUPANÇA --------");
        }

        if (cliente.getContaCorrente() != null)
            System.out.printf("SALDO DISPONIVEL CONTA CORRENTE: %.2f\n", cliente.getContaCorrente().getSaldo());
        if (cliente.getContaPoupanca() != null)
            System.out.printf("SALDO DISPONIVEL CONTA POUPANÇA: %.2f\n", cliente.getContaPoupanca().getSaldo());

    }

    private static void mostrarExtratoConta(Conta conta) {

        for (Transferencia i : conta.getTransferencias()) {

            if (i.getTipoTransferencia() == TipoTransferencia.PIX)
                mostrarTransferenciaPix(i);
            else if (i.getTipoTransferencia() == TipoTransferencia.DEPOSITO)
                mostrarDeposito(i);
        }
    }

    private static void mostrarDeposito(Transferencia i) {
        System.out.println(".................INICIO TRANSAÇÃO DEPOSITO.................");
        System.out.println("--------" + i.getTipoTransferencia() + "--------");
        System.out.println("DATA:" + i.getDataTransferencia().toString().substring(0, 19));
        System.out.printf("VALOR DEPÓSITO: %.2f\n", i.getValorTransferido());
        System.out.println("\n---------------- DESTINO ----------------\n");

        System.out.println("NOME DESTINO:" + i.getContaDestino().getNome());
        System.out.println("CPF DESTINO:" + i.getContaDestino().getCpfCliente());
        System.out.println("AGENCIA DESTINO:" + i.getContaDestino().getNumeroAgencia());
        System.out.println("CONTA DESTINO:" + i.getContaDestino().getNumeroConta());
        System.out.println("DIGITO DESTINO:" + i.getContaDestino().getNumeroDigito());
        System.out.println("TIPO CONTA DESTINO:" + i.getContaDestino().getTipoConta());

        System.out.println("..................FIM TRANSAÇÃO DEPOSITO..................\n");

    }

    private static void mostrarTransferenciaPix(Transferencia i) {
        System.out.println("..................INICIO TRANSAÇÃO PIX..................\n");
        System.out.println("--------" + i.getTipoTransferencia() + "--------");
        System.out.println("DATA:" + i.getDataTransferencia().toString().substring(0, 19));
        System.out.printf("VALOR PIX: %.2f\n", i.getValorTransferido());

        System.out.println("\n-------------- ORIGEM ----------------\n");

        System.out.println("NOME ORIGEM:" + i.getContaOrigem().getNome());
        System.out.println("CPF ORIGEM:" + i.getContaOrigem().getCpfCliente());
        System.out.println("AGENCIA ORIGEM:" + i.getContaOrigem().getNumeroAgencia());
        System.out.println("CONTA ORIGEM:" + i.getContaOrigem().getNumeroConta());
        System.out.println("DIGITO ORIGEM:" + i.getContaOrigem().getNumeroDigito());
        System.out.println("TIPO CONTA ORIGEM:" + i.getContaOrigem().getTipoConta());

        System.out.println("\n---------------- DESTINO ----------------\n");

        System.out.println("NOME DESTINO:" + i.getContaDestino().getNome());
        System.out.println("CPF DESTINO:" + i.getContaDestino().getCpfCliente());
        System.out.println("AGENCIA DESTINO:" + i.getContaDestino().getNumeroAgencia());
        System.out.println("CONTA DESTINO:" + i.getContaDestino().getNumeroConta());
        System.out.println("DIGITO DESTINO:" + i.getContaDestino().getNumeroDigito());
        System.out.println("TIPO CONTA DESTINO:" + i.getContaDestino().getTipoConta());

        System.out.println("...................FIM TRANSAÇÃO PIX...................\n");


    }

    private static void transferenciaPIX(Scanner input, Cliente cliente) {
        while (true) {

            Conta contaDestino = null;
            Cliente clienteDest = null;

            while (true) {
                String cpfCliente = validarEntradaPreenchida(input, "Digite o CPF do cliente que deseja realizar o PIX (ex:000.000.000-00)\nPara sair digite: -1",
                        "CPF vazio, preencha o CPF");

                if (cpfCliente.trim().equals("-1")) {
                    return;
                }

                if (!FuncoesUtil.validarFormatoCpf(cpfCliente)) {
                    System.err.println("Erro! O CPF deve estar com formatação correta.");
                    continue;
                }

                if (!FuncoesUtil.validarCPF(cpfCliente)) {
                    System.err.println("CPF invalido, utilize um CPF valido");
                    continue;
                }

                clienteDest = clienteService.consultarClientePorCpf(cpfCliente);

                if (clienteDest == null) {
                    System.err.println("Cliente não está cadastrado no banco");
                    continue;
                }

                if (clienteDest.getCpfCliente().equals(cliente.getCpfCliente())) {
                    System.err.println("Não é permitido fazer PIX para si mesmo");
                    continue;
                }

                boolean temContaC = clienteDest.getContaCorrente() != null;
                boolean temContaP = clienteDest.getContaPoupanca() != null;

                System.out.println("Destinatario");
                System.out.println("NOME:" + clienteDest.getNomeCliente());
                System.out.println("CPF:" + clienteDest.getCpfCliente());

                mostrarInformacoesDisponivel(clienteDest, temContaC, temContaP);

                contaDestino = escolherContaDestino(input, temContaC, temContaP, clienteDest);

                if (contaDestino == null) {
                    continue;
                }

                break;
            }

            while (true) {
                System.out.println("Saldo Disponivel em conta:");

                boolean temContaCorrente = cliente.getContaCorrente() != null;
                if (temContaCorrente)
                    System.out.printf("(1) Conta CORRENTE: %.2f\n", cliente.getContaCorrente().getSaldo());

                boolean temContaPoupanca = cliente.getContaPoupanca() != null;
                if (temContaPoupanca)
                    System.out.printf("(2) Conta POUPANÇA: %.2f\n", cliente.getContaPoupanca().getSaldo());

                System.out.println("Escolha a conta que deseja utilizar para realizar o PIX:");
                System.out.println("Para Cancelar digite: -1");

                String opcaoConta = validarEntradaPreenchida(input, "Digite a opção desejada)",
                        "Opção não preenchida");

                if (!FuncoesUtil.ehNumero(opcaoConta)) {
                    System.err.println("Erro, somente deve ser preenchido com números (ex:1)");
                    continue;
                }

                int opcao = Integer.parseInt(opcaoConta);

                if (opcao == -1) {
                    break;
                }

                if (opcao == 1 && temContaCorrente) {
                    if (!realizarTransferencia(input, cliente, clienteDest, contaDestino, cliente.getContaCorrente())) {
                        System.err.println("Erro ao realizar PIX");
                        break;
                    }
                    System.out.println("PIX Realizado com Sucesso");
                    return;
                } else if (opcao == 2 && temContaPoupanca) {
                    if (!realizarTransferencia(input, cliente, clienteDest, contaDestino, cliente.getContaPoupanca())) {
                        System.err.println("Erro ao realizar PIX");
                        break;
                    }
                    System.out.println("PIX Realizado com Sucesso");
                    return;
                } else {
                    System.err.println("Opção invalida");
                }
            }

        }
    }

    private static void mostrarInformacoesDisponivel(Cliente cliente, boolean temContaC, boolean temContaP) {
        if (temContaC) {
            System.out.println("\nAgência:" + cliente.getContaCorrente().getNumeroAgencia());
            System.out.println("Conta Corrente:" + cliente.getContaCorrente().getNumeroConta());
            System.out.println("Digito:" + cliente.getContaCorrente().getDigitoConta());
        }

        if (temContaP) {
            System.out.println("\nAgência:" + cliente.getContaPoupanca().getNumeroAgencia());
            System.out.println("Conta Poupança:" + cliente.getContaPoupanca().getNumeroConta());
            System.out.println("Digito:" + cliente.getContaPoupanca().getDigitoConta());
        }
    }

    private static boolean realizarTransferencia(Scanner input, Cliente cliente, Cliente clienteDest, Conta contaDestino, Conta contaOrigem) {

        while (true) {
            String valorTransf = validarEntradaPreenchida(input, "Digite o valor que deseja transferir utilizando ponto (ex:1.50)\nPara sair digite: -1",
                    "O valor precisa ser preenchido");

            if (!FuncoesUtil.ehNumero(valorTransf)) {
                System.err.println("Erro, utilize número e ponto no lugar da vírgula ");
                continue;
            }

            double valor = Double.parseDouble(valorTransf);

            if (valor == -1) {
                return false;
            }

            if (valor <= 0) {
                System.err.println("É necessario que o valor da PIX seja maior que zero");
                continue;
            }

            if (valor > contaOrigem.getSaldo()) {
                System.err.println("Saldo insuficiente para completar a transação");
                continue;
            }

            return transferenciaService.transferir(cliente, clienteDest, contaDestino, contaOrigem, valor);
        }

    }

    private static Conta escolherContaDestino(Scanner input, boolean temContaC, boolean temContaP, Cliente clienteDest) {
        while (true) {
            System.out.println("Escolha a conta de destino:");
            System.out.println("-1 para cancelar e buscar outro destinatario");

            if (temContaC)
                System.out.println("(1) Conta Corrente");
            if (temContaP)
                System.out.println("(2) Conta Poupança");

            String opConta = input.nextLine();

            if (opConta.isBlank()) {
                System.err.println("É necessario que seja preenchido com o número informado no menu");
                continue;
            }

            if (!FuncoesUtil.ehNumero(opConta)) {
                System.err.println("Erro, só é permitido numeros");
                continue;
            }

            int opcao = Integer.parseInt(opConta);

            if (opcao == -1) {
                return null;
            }

            if (temContaC && opcao == 1) {
                return clienteDest.getContaCorrente();
            } else if (temContaP && opcao == 2) {
                return clienteDest.getContaPoupanca();
            } else {
                System.err.println("Digite uma opção valida");
            }

        }
    }

    public static void menuPIX(Scanner input, Cliente cliente) {

        while (true) {
            System.out.println("\n=== PIX ===");
            System.out.println("(1) Enviar PIX");
            System.out.println("(9) Voltar para menu anterior");
            System.out.println("Escolha a opção desejada: ");

            String transferenciaPix = input.nextLine();
            if (!FuncoesUtil.ehNumero(transferenciaPix)) {
                System.err.println("Entrada inválida, utilize somente os números!");
                continue;
            }
            int pix = Integer.parseInt(transferenciaPix);

            switch (pix) {
                case 1:
                    System.out.println("== Enviar PIX ==");
                    transferenciaPIX(input, cliente);
                    //  enviar PIX
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

            System.out.println(cliente.toString());

            System.out.println("(1) Atualização Cadastral");
            System.out.println("(2) Alterar Categoria");
            System.out.println("(9) Voltar para o menu anterior");
            System.out.println("Escolha a opção desejada: ");

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
        escolherCategoria(input, cliente);

        if (cliente.getContaPoupanca() != null)
            definirRendimentoPoupanca(cliente, cliente.getContaPoupanca());
        if (cliente.getContaCorrente() != null)
            definirTaxaManutencao(cliente, cliente.getContaCorrente());

        clienteService.atualizarCliente(cliente);
    }

    public static void alterarDados(Scanner input, Cliente cliente) {

        while (true) {
            System.out.println("\n== Perfil ==");
            System.out.println("(1) Nome de preferência");
            System.out.println("(2) Data de nascimento");
            System.out.println("(3) Endereço");
            System.out.println("(4) Senha");
            System.out.println("(9) Voltar para o menu anterior");
            System.out.println("Escolha a opção desejada: ");

            String AlterarUsuario = input.nextLine();
            if (!FuncoesUtil.ehNumero(AlterarUsuario)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int alterar = Integer.parseInt(AlterarUsuario);

            switch (alterar) {
                case 1:

                    do {
                        String novoNome = validarEntradaPreenchida(input,
                                "Digite o nome desejado",
                                "Nome não preenchido");
                        if (!FuncoesUtil.validarNomeCliente(novoNome)) {
                            System.err.println("Nome inválido! O nome deve conter somente letras, com no mínimo 2 e no máximo 100 caracteres.");
                            continue;
                        }
                        cliente.setNomeCliente(novoNome);
                        clienteService.alterarDadosCliente(cliente);
                        System.out.println("Nome alterado com sucesso!");
                        break;
                    } while (true);

                    break;
                case 2:

                    preencherDataNascimento(input, cliente);
                    clienteService.alterarDadosCliente(cliente);
                    System.out.println("Data de nascimento alterado com sucesso!");
                    break;

                case 3:
                    preencherEndereco(input, cliente);
                    clienteService.alterarDadosCliente(cliente);
                    System.out.println("Endereço alterado com sucesso!");
                    break;

                case 4:
                    System.out.println("Deseja alterar sua senha?");
                    clienteUpdateSenhaMenu(input, cliente);
                    break;
                case 9:

                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    private static void clienteUpdateSenhaMenu(Scanner input, Cliente cliente) {
        while (true) {
            System.out.println("(1) Prosseguir com alteração da senha");
            System.out.println("(2) Cancelar Operação");
            String alterarSenha = input.nextLine();
            if (!FuncoesUtil.ehNumero(alterarSenha)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int alterarSenhaInt = Integer.parseInt(alterarSenha);
            switch (alterarSenhaInt) {
                case 1:
                    clientPasswordUpdate(input, cliente);
                    return;
                case 2:
                    return;
                default:
                    System.err.println("Opção inválida. Escolha apenas as opções disponíveis");

            }

        }
    }

    private static void clientPasswordUpdate(Scanner input, Cliente cliente) {
        System.out.println("Digite sua senha atual");
        String senhaAtual = input.nextLine();
        while (true) {
            if (!clienteService.checkSenha(cliente, senhaAtual)) {
                System.out.println("Senha Incorreta! - 1 Tentativa restante");
                System.out.println("Digite sua senha atual");
                senhaAtual = input.nextLine();
                if (!clienteService.checkSenha(cliente, senhaAtual)) {
                    System.out.println("Senha Incorreta!");
                    System.out.println("Operação cancelada.");
                    return;
                }

            } else {
                preencherSenha(input, cliente);
                String novaSenha = cliente.getSenhaCliente();
                clienteService.clientPasswordUpdate(cliente, novaSenha);
                System.out.println("Senha Alterada com Sucesso!");
                return;
            }


        }

    }

    //menu para controle de cartao
    public static void menuEscolhaContaCartao(Scanner input, Cliente cliente) {
        while (true) {

            System.out.println("Escolha a conta para administrar o cartão");

            boolean temContaCorrente = cliente.getContaCorrente() != null;
            boolean temContaPoupanca = cliente.getContaPoupanca() != null;

            if (temContaPoupanca)
                System.out.println("(1) Conta Poupança");

            if (temContaCorrente)
                System.out.println("(2) Conta Corrente");

            System.out.println("(9) Voltar Para o Menu Anterior");

            String escolherCartaoMenu = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolherCartaoMenu)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int escolherConta = Integer.parseInt(escolherCartaoMenu);

            if (temContaCorrente && escolherConta == 2) {
                menuCartao(input, cliente, cliente.getContaCorrente());
            } else if (temContaPoupanca && escolherConta == 1) {
                menuCartao(input, cliente, cliente.getContaPoupanca());
            } else if (escolherConta == 9) {
                return;
            } else {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
            }
        }

    }

    private static void menuCartao(Scanner input, Cliente cliente, Conta conta) {
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
            int escolherConta = Integer.parseInt(escolherCartaoMenu);

            switch (escolherConta) {
                //cartao de debito
                case 1:
                    menuCartaoDebito(input, cliente, conta);
                    break;
                //cartao de credito
                case 2:
                    menuCartaoCredito(input, cliente, conta);
                    break;
                //Voltar menu
                case 9:
                    return;

            }
        }
    }

    public static void menuCartaoDebito(Scanner input, Cliente cliente, Conta conta) {
        while (true) {

            System.out.println("\nCARTÕES:");
            listarCartao(conta.getCartaoDebito());

            System.out.println("\n\n== Cartão de Debito ==");
            System.out.println("(1) Adquirir Cartão");
            System.out.println("(2) Alterar Limite Diário");
            System.out.println("(3) Cancelar Cartão");
            System.out.println("(9) Voltar Para o Menu Anterior");

            System.out.print("");

            String escolherDebitoMenu = input.nextLine();
            if (!FuncoesUtil.ehNumero(escolherDebitoMenu)) {
                System.err.println("Opção inválida, utilize somente os números mostrados no Menu!");
                continue;
            }
            int escolherDebitoMenuInt = Integer.parseInt(escolherDebitoMenu);

            switch (escolherDebitoMenuInt) {
                //adquirir cartao
                case 1:
                    adquirirCartaoDebito(input, cliente, conta);
                    break;
                //Alterar limite diario
                case 2:
                    alterarLimiteDiario(input, cliente, conta.getCartaoDebito());
                    break;
                //cancelar cartao
                case 3:
                    cancelarCartaoDebito(input, cliente, conta.getCartaoDebito());
                    break;
                //Voltar menu
                case 9:
                    return;

            }
        }
    }

    private static void listarCartao(List<Cartao> cartao) {
        for (int i = 0; i < cartao.size(); i++) {
            Cartao c = cartao.get(i);
            System.out.printf("CODIGO:%d NUMERO:%s TIPO:%s CVV:%s VENCIMENTO:%s LIMITE:%.2f STATUS:%s\n",
                    i,
                    c.getNumeroCartao(), c.getTipoCartao().toString(),
                    c.getCvvCartao(),
                    c.getDataVencimento().format(DateTimeFormatter.ISO_DATE),
                    c.getValorLimite(), c.getStatus());
        }
        System.out.println();
    }

    private static void cancelarCartaoDebito(Scanner input,
                                             Cliente cliente,
                                             List<Cartao> cartao) {

        List<Cartao> listaFiltrada = somenteCartaoValido(cartao);

        if (listaFiltrada.isEmpty()) {
            System.err.println("Não há cartão para ser cancelado");
            return;
        }

        while (true) {
            for (int i = 0; i < listaFiltrada.size(); i++) {
                Cartao c = listaFiltrada.get(i);

                if (c.getStatus() == CartaoStatus.CANCELADO)
                    continue;

                System.out.printf("CODIGO:%d NUMERO:%s TIPO:%s CVV:%s VENCIMENTO:%s LIMITE:%.2f STATUS:%s\n",
                        i,
                        c.getNumeroCartao(), c.getTipoCartao().toString(),
                        c.getCvvCartao(),
                        c.getDataVencimento().format(DateTimeFormatter.ISO_DATE),
                        c.getValorLimite(), c.getStatus());
            }

            System.out.println("Escolha o cartao que deseja cancelar ou -1 para sair");
            String cartaoEscolhido = input.nextLine();

            if (!FuncoesUtil.ehNumero(cartaoEscolhido)) {
                System.err.println("Error! Somente numero");
                continue;
            }

            int codigoCartao = Integer.parseInt(cartaoEscolhido);

            if (codigoCartao == -1)
                return;

            Cartao c;
            try {
                c = listaFiltrada.get(codigoCartao);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Código do cartão invalido");
                continue;
            }

            if (!cartaoService.cancelarCartao(cliente, c)) {
                System.err.println("Falha ao cancelar o cartao");
                continue;
            }
            break;
        }
    }

    private static List<Cartao> somenteCartaoValido(List<Cartao> cartao) {
        List<Cartao> listaFiltrada = new ArrayList<>();

        for (Cartao c : cartao) {
            if (c.getStatus() != CartaoStatus.CANCELADO)
                listaFiltrada.add(c);
        }
        return listaFiltrada;
    }

    private static void alterarLimiteDiario(Scanner input,
                                            Cliente cliente,
                                            List<Cartao> cartao) {
        List<Cartao> listaFiltrada = somenteCartaoValido(cartao);

        while (true) {
            listarCartao(listaFiltrada);

            System.out.println("Digite o codigo do cartao que deseja alterar o limite diario ou -1 para sair");
            String cartaoEscolhido = input.nextLine();

            if (!FuncoesUtil.ehNumero(cartaoEscolhido)) {
                System.err.println("Error! Somente numero");
                continue;
            }

            int codigoCartao = Integer.parseInt(cartaoEscolhido);

            if (codigoCartao == -1)
                return;

            Cartao c;
            try {
                c = listaFiltrada.get(codigoCartao);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Código do cartão invalido");
                continue;
            }

            while (true) {
                switch (cliente.getCategoria()) {
                    case SUPER:
                        System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                        System.out.println("Valor minímo: R$1000,00 , Valor máximo: SEM LIMITE");
                        break;
                    case PREMIUM:
                        System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                        System.out.println("Valor minímo: R$1000,00 , Valor máximo: R$30.000,00");
                        break;
                    case COMUM:
                        System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                        System.out.println("Valor minímo: R$1000,00 , Valor máximo: R$15.000,00");
                        break;
                }

                System.out.println("Digite o novo valor de limite diario");
                String limDiario = input.nextLine();

                if (!FuncoesUtil.ehNumero(limDiario)) {
                    System.err.println("Error! Somente numero");
                    continue;
                }

                double novoLimite = Double.parseDouble(limDiario);

                if (!cartaoService.alterarLimiteDiario(cliente, c, novoLimite)) {
                    System.err.println("Valor de limite inválido com sua categoria");
                    continue;
                }
                System.out.println("Limite alterado com sucesso");
                break;
            }
        }
    }

    private static void alterarLimiteCredito(Scanner input,
                                             Cliente cliente,
                                             List<Cartao> cartao) {
        List<Cartao> listaFiltrada = somenteCartaoValido(cartao);

        while (true) {
            listarCartao(listaFiltrada);

            System.out.println("Digite o codigo do cartao que deseja alterar o limite diario ou -1 para sair");
            String cartaoEscolhido = input.nextLine();

            if (!FuncoesUtil.ehNumero(cartaoEscolhido)) {
                System.err.println("Error! Somente numero");
                continue;
            }

            int codigoCartao = Integer.parseInt(cartaoEscolhido);

            if (codigoCartao == -1)
                return;

            Cartao c;
            try {
                c = listaFiltrada.get(codigoCartao);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Código do cartão invalido");
                continue;
            }

            while (true) {
                switch (cliente.getCategoria()) {
                    case SUPER:
                        System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                        System.out.println("Valor minímo: R$5000,00 , Valor máximo: R$ 10000,00");
                        break;
                    case PREMIUM:
                        System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                        System.out.println("Valor minímo: R$10000,00 , Valor máximo: SEM LIMITE");
                        break;
                    case COMUM:
                        System.out.println("Valores disponíveis para categoria " + cliente.getCategoria() + ":");
                        System.out.println("Valor minímo: R$1000,00 , Valor máximo: R$5.000,00");
                        break;
                }

                System.out.println("Digite o novo valor de limite ");
                String limDiario = input.nextLine();

                if (!FuncoesUtil.ehNumero(limDiario)) {
                    System.err.println("Error! Somente numero");
                    continue;
                }

                double novoLimite = Double.parseDouble(limDiario);

                if (!cartaoService.alterarLimiteCredito(cliente, c, novoLimite)) {
                    System.err.println("Valor de limite inválido com sua categoria");
                    continue;
                }
                System.out.println("Limite alterado com sucesso");
                break;
            }
        }
    }


    private static void adquirirCartaoDebito(Scanner input, Cliente cliente, Conta conta) {
        Cartao cartaoCriado = cartaoService.adquirirCartaoDebito(cliente, conta);

        if (cartaoCriado == null) {
            System.err.println("Erro ao criar cartão de débito, tente novamente mais tarde");
            return;
        }

        System.out.println(cartaoCriado);

    }

    private static Seguro escolherSeguroViagemParaCartao(Scanner input, Cliente cliente, Cartao cartaoCriado) {
        while (true) {

            String escolhaSeguro = validarEntradaPreenchida(input, "Deseja adicionar o seguro viagem ? Por apenas R$ 50,00 por mês \nResponda 1 para SIM\nResponda 2 para NÃO",
                    "Precisa ser preenchido a resposta do seguro");
            if (!FuncoesUtil.ehNumero(escolhaSeguro)) {
                System.err.println("Opção inválida, digite apenas numeros!");
                continue;
            }

            int escolha = Integer.parseInt(escolhaSeguro);

            switch (escolha) {
                case 1:
                    return seguroService.criarSeguroViagemCobrado(cliente, cartaoCriado);
                case 2:
                    return null;
                default:
                    System.err.println("Opção invalida, digite apenas 1 ou 2");
            }

        }
    }

    public static void menuCartaoCredito(Scanner input, Cliente cliente, Conta conta) {
        while (true) {

            System.out.println("CARTÕES:");
            listarCartao(conta.getCartaoCredito());

            System.out.println("\n\n== Cartão de Crédito ==");
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
                    Cartao cartaoCriado = cartaoService.adquirirCartaoCredito(cliente, conta);
                    Seguro seguroViagem;

                    if (cliente.getCategoria() == CategoriaEnum.PREMIUM) {
                        seguroViagem = seguroService.criarSeguroViagemSemCobranca(cliente, cartaoCriado);
                    } else {
                        seguroViagem = escolherSeguroViagemParaCartao(input, cliente, cartaoCriado);
                    }
                    System.out.println("---------------------- CARTÃO DE CRÉDITO ---------------------");
                    System.out.println(cartaoCriado + "\n");

                    if (seguroViagem != null) {
                        System.out.println("---------------------- SEGURO VIAGEM ---------------------");
                        System.out.println(seguroViagem + "\n");
                    }

                    Seguro seguroFraude = seguroService.criarSeguroFraude(cliente, cartaoCriado);
                    System.out.println("---------------------- SEGURO FRAUDE ---------------------");
                    System.out.println(seguroFraude + "\n");
                    break;
                //alterar limite
                case 2:
                    alterarLimiteCredito(input, cliente, conta.getCartaoCredito());
                    break;
                //menu de seguro do cartao
                case 3:
                    Cartao cartaoEscolhido = escolherCartaoDeCredito(input, conta);
                    if (cartaoEscolhido != null)
                        menuSeguro(input, cliente, conta, cartaoEscolhido);
                    break;
                //cancelar cartao
                case 4:
                    cancelarCartaoDebito(input, cliente, conta.getCartaoCredito());
                    break;
                //Voltar menu
                case 9:
                    return;

            }
        }
    }

    public static void menuSeguro(Scanner input, Cliente cliente, Conta conta, Cartao cartao) {

        while (true) {
            System.out.println("\n== Meu Seguro ==");
            System.out.println("(1) Adquirir Seguro Viagem");
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
                    if (naoPossuiSeguroViagem(cartao)) {
                        escolherSeguroViagemParaCartao(input, cliente, cartao);
                        System.out.println("Seguro adicionado com sucesso!");
                    }
                    break;
                //consultar seguro
                case 2:
                    listarSegurosAdquiridos(cartao, cartao.getSeguros());
                    break;
                //cancelar seguro
                case 3:
                    cancelarSeguro(input, cliente, cartao);
                    break;
                //Voltar menu
                case 9:
                    return;

            }

        }
    }

    private static boolean naoPossuiSeguroViagem(Cartao cartao) {

        for (Seguro s : cartao.getSeguros()) {
            if (s.getDescricaoCobertura().equals(SeguroService.SEGURO_VIAGEM_DESCRICAO)) {
                System.err.println("O cartão já possui seguro viagem");
                return false;
            }
        }

        return true;
    }

    private static void cancelarSeguro(Scanner input, Cliente cliente, Cartao cartao) {
        while (true) {
            List<Seguro> segurosViagens = filtrarSeguroViagem(cartao);

            if (segurosViagens.isEmpty()) {
                System.err.println("Não há seguros para cancelar");
                return;
            }

            listarSegurosAdquiridos(cartao, segurosViagens);

            System.out.println("Digite o codigo do seguro que deseja alterar o limite diario ou -1 para sair");
            String seguroEscolhido = input.nextLine();

            if (!FuncoesUtil.ehNumero(seguroEscolhido)) {
                System.err.println("Error! Somente numero");
                continue;
            }

            int codigoSeguro = Integer.parseInt(seguroEscolhido);

            if (codigoSeguro == -1)
                return;

            if (!seguroService.cancelarSeguro(cliente, cartao, segurosViagens, codigoSeguro)) {
                System.err.println("Erro ao cancelar o seguro, código invalido");
                continue;
            }

            System.out.println("Seguro cancelado com sucesso");
            break;
        }
    }

    private static void listarSegurosAdquiridos(Cartao cartao, List<Seguro> seguros) {

        System.out.println("Cartão: " + cartao.getNumeroCartao());
        for (int i = 0; i < seguros.size(); i++) {
            Seguro s = seguros.get(i);
            System.out.println("-----------------------------------");
            System.out.printf("CODIGO: %d Apolice: %s, \nCobertura: %s Data de Contratacao: %s \n",
                    i,
                    s.getNumeroApolice(),
                    s.getDescricaoCobertura(),
                    s.getDataContratacao().format(DateTimeFormatter.ISO_DATE));

            System.out.printf("Inicio da vigencia: %s Fim da vigencia: %s \n",
                    s.getInicioVigencia().format(DateTimeFormatter.ISO_DATE),
                    s.getFimVigencia().format(DateTimeFormatter.ISO_DATE));

            System.out.printf("Valor do Seguro: %.2f Valor da Indenização: %.2f \n",
                    s.getValorSeguro(), s.getValorIndenizacao());
            System.out.println("-----------------------------------");
        }
    }

    private static List<Seguro> filtrarSeguroViagem(Cartao cartao) {
        List<Seguro> seguros = new ArrayList<>();
        for (Seguro s : cartao.getSeguros()) {
            if (s.getDescricaoCobertura().equals(SeguroService.SEGURO_VIAGEM_DESCRICAO))
                seguros.add(s);
        }
        return seguros;
    }

    private static Cartao escolherCartaoDeCredito(Scanner input, Conta conta) {
        List<Cartao> listaFiltrada = somenteCartaoValido(conta.getCartaoCredito());

        while (true) {
            listarCartao(listaFiltrada);

            String codigo = validarEntradaPreenchida(input, "Digite o código do cartao que deseja acessar seus seguros, ou -1 para voltar",
                    "O código do seguro deve ser preenchida");

            if (!FuncoesUtil.ehNumero(codigo)) {
                System.err.println("O código precisa ser um numero");
                continue;
            }

            int codigoCartao = Integer.parseInt(codigo);

            if (codigoCartao == -1)
                return null;

            Cartao c;
            try {
                c = listaFiltrada.get(codigoCartao);
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Código do cartão invalido");
                continue;
            }

            return c;
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

