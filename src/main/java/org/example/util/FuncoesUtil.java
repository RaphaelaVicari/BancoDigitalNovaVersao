package org.example.util;

import java.util.regex.Pattern;

public class FuncoesUtil {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

    private FuncoesUtil(){}

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

}
