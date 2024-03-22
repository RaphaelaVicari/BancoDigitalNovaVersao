package org.example.service;

import org.example.model.*;
import org.example.repository.ClienteRepository;

import java.time.LocalDateTime;

public class TransferenciaService {
    private final ClienteService clienteRepository;

    public TransferenciaService(ClienteService clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean transferir(Cliente cliente, Cliente clienteDest, Conta contaDestino, Conta contaOrigem, double valor) {

        if(valor <= 0){
            return false;
        }

        if(valor > contaOrigem.getSaldo()){
            return false;
        }

        ContaTransferencia contaTransferencia = createContaTransferencia(clienteDest, contaDestino, valor);
        ContaTransferencia contaOrigemTransf = createContaTransferencia(cliente, contaOrigem, valor * -1);

        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setContaDestino(contaTransferencia);
        transferencia.setContaOrigem(contaOrigemTransf);
        transferencia.setTipoTransferencia(TipoTransferencia.PIX);
        transferencia.setValorTransferido(valor);

        contaOrigem.adicionarTransferencia(transferencia);
        contaDestino.adicionarTransferencia(transferencia);

        contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);

        return clienteRepository.atualizarCliente(cliente) != null;
    }

    private static ContaTransferencia createContaTransferencia(Cliente clienteDest,
                                                               Conta contaDestino,
                                                               double valor) {
        ContaTransferencia contaTransferencia = new ContaTransferencia();
        contaTransferencia.setTipoConta(contaDestino.getTipoConta());
        contaTransferencia.setNumeroConta(contaDestino.getNumeroConta());
        contaTransferencia.setNumeroDigito(contaDestino.getDigitoConta());
        contaTransferencia.setNumeroAgencia(contaDestino.getNumeroAgencia());
        contaTransferencia.setCpfCliente(clienteDest.getCpfCliente());
        contaTransferencia.setNome(clienteDest.getNomeCliente());
        contaTransferencia.setValor(valor);
        return contaTransferencia;
    }

    public void depositar(Cliente cliente, Conta conta, double valor) {

        ContaTransferencia contaTransferencia = createContaTransferencia(cliente, conta, valor);

        Transferencia transferencia = new Transferencia();
        transferencia.setDataTransferencia(LocalDateTime.now());
        transferencia.setContaDestino(contaTransferencia);
        transferencia.setContaOrigem(contaTransferencia);
        transferencia.setTipoTransferencia(TipoTransferencia.DEPOSITO);
        transferencia.setValorTransferido(valor);

        conta.adicionarTransferencia(transferencia);

        conta.setSaldo(conta.getSaldo() + valor);
        clienteRepository.atualizarCliente(cliente);
    }
}
