package org.example.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Cliente;
import org.example.util.RepositoryUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClienteRepository {

    private static final String CLIENTES_JSON = "./clientes.json";
    private Map<String, Cliente> clienteMap;
    private RepositoryUtil utilidades;

    private ObjectMapper mapeador;


    public ClienteRepository() {
        utilidades = new RepositoryUtil();
        mapeador = new ObjectMapper();

        try {
            byte[] dados = utilidades.lerArquivo(CLIENTES_JSON);
            clienteMap = mapeador.readValue(dados, new TypeReference<>() {
            });
        } catch (IOException e) {
            clienteMap = new HashMap<>();
        }
    }

    public Cliente cadastrarCliente(Cliente registroNovoCliente) {


        try {
            clienteMap.put(registroNovoCliente.getCpfCliente(), registroNovoCliente);
            String saida = mapeador.writerWithDefaultPrettyPrinter().writeValueAsString(clienteMap);
            utilidades.persistirArquivo(CLIENTES_JSON, saida);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return registroNovoCliente;
    }


    public Cliente consultarClientePorCpf(String cpfCliente) {

        return clienteMap.get(cpfCliente);
    }
    public boolean atualizarBaseDados() {
        try {
            String saida = mapeador.writerWithDefaultPrettyPrinter().writeValueAsString(clienteMap);
            utilidades.persistirArquivo(CLIENTES_JSON, saida);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}
