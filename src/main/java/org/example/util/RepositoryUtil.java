package org.example.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RepositoryUtil {
    public void persistirArquivo(String localArquivo, String conteudo) throws IOException {
        Files.writeString(Paths.get(localArquivo), conteudo, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
    }
    
    public byte[] lerArquivo(String value) throws IOException {
        byte[] valor = Files.readAllBytes(Paths.get(value));
        return valor;
    }
}
