package br.com.academia.observer;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import br.com.academia.model.CheckIn;

/**
 * OBSERVER (papel: Observador Concreto).
 * <p>
 * Avisa a recepção que um aluno entrou na academia (apenas no console).
 */
@Component
public class NotificacaoRecepcao implements ObservadorCheckIn {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void aoRegistrarCheckIn(CheckIn checkIn) {
        System.out.println("[Recepção] " + checkIn.getAluno().getNome()
                + " (Plano " + checkIn.getAluno().getPlano().getNome() + ") entrou às "
                + checkIn.getDataHora().format(HORA) + ".");
    }
}
