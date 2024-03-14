package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Cliente;
import org.example.util.RepositoryUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteRepository {

    private static final String CLIENTES_JSON = "./clientes.json";
    private Map<Long, Cliente> clienteList;

    private RepositoryUtil utilidades;

    private ObjectMapper mapeador;


    public ClienteRepository() {
        utilidades = new RepositoryUtil();
        mapeador = new ObjectMapper();

        try {
            byte[] dados = utilidades.lerArquivo(CLIENTES_JSON);
            clienteList = mapeador.readValue(dados, new TypeReference<>() {
            });
        } catch (IOException e) {
            clienteList = new HashMap<>();
        }
    }

    public Cliente cadastrarCliente(Cliente registroNovoCliente) {


        try {
            clienteList.put((long) Math.random(), registroNovoCliente);
            String saida = mapeador.writerWithDefaultPrettyPrinter().writeValueAsString(clienteList);
            utilidades.persistirArquivo(CLIENTES_JSON, saida);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return registroNovoCliente;
    }


    public Cliente consultarClientePorCpf(String cpfCliente) {

        for (Map.Entry<Long, Cliente> d : clienteList.entrySet()) {
            var dados = d.getValue();
            if (dados.getCpfCliente().equalsIgnoreCase(cpfCliente)) {
                return dados;
            }
        }
        return null;
    }
    public boolean atualizarBaseDados() {
        try {
            String saida = mapeador.writerWithDefaultPrettyPrinter().writeValueAsString(clienteList);
            utilidades.persistirArquivo(CLIENTES_JSON, saida);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}
