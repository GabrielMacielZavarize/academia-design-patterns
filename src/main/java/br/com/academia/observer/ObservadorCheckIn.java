package br.com.academia.observer;

import br.com.academia.model.CheckIn;

/**
 * OBSERVER (papel: Observador).
 * <p>
 * Contrato de toda notificação de check-in. Para criar uma nova notificação,
 * basta criar uma classe {@code @Component} que implemente esta interface:
 * o {@link PublicadorCheckIn} passa a avisá-la automaticamente.
 */
public interface ObservadorCheckIn {

    // Chamado pelo PublicadorCheckIn sempre que um check-in é registrado.
    void aoRegistrarCheckIn(CheckIn checkIn);
}
