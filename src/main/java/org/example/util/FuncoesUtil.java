package org.example.util;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuncoesUtil {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static String formatarColuna(String nomeColuna, int tamanhoColuna) {
        if (nomeColuna.length() > tamanhoColuna) {
            nomeColuna = nomeColuna.substring(0, tamanhoColuna);
        }

        return nomeColuna + " ".repeat(tamanhoColuna - nomeColuna.length()) + "|";
    }

    public static boolean ehNumero(String entrada) {
        if (entrada == null) {
            return false;
        }
        return NUMBER_PATTERN.matcher(entrada).matches();
    }

    public static boolean validarNomeCliente(String nome) {
        String padrao = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]{2,100}$";

        Pattern pattern = Pattern.compile(padrao);
        Matcher matcher = pattern.matcher(nome);

        return matcher.matches();
    }

    public static boolean validarSenha(String senha) {
        // Expressão regular para validar senha com exatamente 4 dígitos
        String regexSenha = "\\d{4}";

        Pattern pattern = Pattern.compile(regexSenha);
        Matcher matcher = pattern.matcher(senha);

        return matcher.matches();
    }

    public static boolean validarUF(String uf) {
        // Expressão regular para validar UF com exatamente duas letras maiúsculas
        String regexUF = "^[A-Z]{2}$";

        Pattern pattern = Pattern.compile(regexUF);
        Matcher matcher = pattern.matcher(uf);

        return matcher.matches();
    }

    public static boolean validarCEP(String cep) {
        // Expressão regular para validar CEP no formato XXXXX-XXX
        String regexCEP = "^\\d{5}-\\d{3}$";

        Pattern pattern = Pattern.compile(regexCEP);
        Matcher matcher = pattern.matcher(cep);

        return matcher.matches();
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

    public static boolean validarCPF(String cliente) {
        CPF cpfUtil = new CPF(cliente);

        if (!cpfUtil.isCPF()) {
            return false;
        }
        return true;
    }

    public static boolean validarFormatoCpf(String cpf) {
        String padrao = "^\\d{3}.\\d{3}.\\d{3}-\\d{2}$";

        Pattern pattern = Pattern.compile(padrao);
        Matcher matcher = pattern.matcher(cpf);

        return matcher.matches();
    }

    public static String randomCardNumberGenerator(int howManyNumbers) {
        Random random = new Random();
        StringBuilder numeroCartao = new StringBuilder();
        for (int i = 0; i < howManyNumbers; i++) {

            int digito = random.nextInt(10);
            numeroCartao.append(digito);
        }
        return numeroCartao.toString();
    }

}
