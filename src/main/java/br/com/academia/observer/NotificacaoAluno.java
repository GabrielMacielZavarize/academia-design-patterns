package br.com.academia.observer;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import br.com.academia.model.CheckIn;

/**
 * OBSERVER (papel: Observador Concreto).
 * <p>
 * Confirma o check-in para o aluno (apenas no console).
 */
@Component
public class NotificacaoAluno implements ObservadorCheckIn {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void aoRegistrarCheckIn(CheckIn checkIn) {
        System.out.println("[Aluno] Olá, " + checkIn.getAluno().getNome()
                + "! Check-in confirmado às " + checkIn.getDataHora().format(HORA) + ". Bom treino!");
    }
}
