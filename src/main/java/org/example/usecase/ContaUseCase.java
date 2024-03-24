package org.example.usecase;

import org.example.model.Cliente;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ContaUseCase {

    private final ClienteUseCase clienteRepository;

    public ContaUseCase(ClienteUseCase clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public double calcularRendimentoFuturoAteOFinalDoMes(Cliente cliente) {
        LocalDate now = LocalDate.now();
        LocalDate endMonth = now.withDayOfMonth(now.lengthOfMonth());
        long daysToEndMonth = now.until(endMonth, ChronoUnit.DAYS);
        int daysToEndMonthInt = (int) daysToEndMonth;

        double taxaPorDia = calcularTaxaPorDia(cliente);

        return calcularRendimento(cliente, daysToEndMonthInt, taxaPorDia);
    }

    public double calcularRendimentoPoupanca(Cliente cliente) {
        LocalDate now = LocalDate.now();
        LocalDate diaPrimeiro = now.withDayOfMonth(1);
        LocalDate finalMes = now.withDayOfMonth(now.lengthOfMonth());
        long dias = diaPrimeiro.until(now, ChronoUnit.DAYS);

        double taxaPorDia = calcularTaxaPorDia(cliente);

        double rendimento = (dias * taxaPorDia) * cliente.getContaPoupanca().getSaldo();

        if(now.getDayOfMonth() == finalMes.getDayOfMonth()) {
            cliente.getContaPoupanca().setSaldo(rendimento + cliente.getContaPoupanca().getSaldo());
        }

        return rendimento;
    }

    public double calcularRendimentoPoupancaProvisaoDias(Cliente cliente, int days) {
        double taxaPorDia = calcularTaxaPorDia(cliente);
        return calcularRendimento(cliente, days, taxaPorDia);
    }

    private static double calcularRendimento(Cliente cliente, int daysToEndMonthInt, double taxaPorDia) {
        return (daysToEndMonthInt * taxaPorDia) * cliente.getContaPoupanca().getSaldo();
    }

    private static double calcularTaxaPorDia(Cliente cliente) {
        return (cliente.getContaPoupanca().getTaxaRendimento() / 30);
    }

    public double calcularCustoContaCorrente(Cliente cliente) {
        LocalDate now = LocalDate.now();
        LocalDate diaPrimeiro = now.withDayOfMonth(1);
        LocalDate finalMes = now.withDayOfMonth(now.lengthOfMonth());
        long dias = diaPrimeiro.until(now, ChronoUnit.DAYS);

        double custoDia = cliente.getContaCorrente().getTaxaManutencao() / 30;
        return custoDia * dias;
    }




}
