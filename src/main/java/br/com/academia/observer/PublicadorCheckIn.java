package br.com.academia.observer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import br.com.academia.model.CheckIn;

/**
 * OBSERVER (papel: Sujeito / Subject).
 * <p>
 * Guarda a lista de observadores e avisa todos eles quando um check-in acontece.
 * Só conhece a interface {@link ObservadorCheckIn}, nunca as notificações concretas.
 */
@Component
public class PublicadorCheckIn {

    // CopyOnWriteArrayList: segura para acessos simultâneos (vários check-ins ao mesmo tempo).
    private final List<ObservadorCheckIn> observadores = new CopyOnWriteArrayList<>();

    // O Spring injeta aqui TODOS os beans (@Component) que implementam ObservadorCheckIn.
    // Por isso, uma nova notificação é registrada só criando uma classe nova.
    public PublicadorCheckIn(List<ObservadorCheckIn> observadoresIniciais) {
        for (ObservadorCheckIn observador : observadoresIniciais) {
            adicionar(observador);
        }
    }

    public void adicionar(ObservadorCheckIn observador) {
        observadores.add(observador);
    }

    public void remover(ObservadorCheckIn observador) {
        observadores.remove(observador);
    }

    // Percorre a lista e avisa cada observador sobre o novo check-in.
    public void notificar(CheckIn checkIn) {
        for (ObservadorCheckIn observador : observadores) {
            observador.aoRegistrarCheckIn(checkIn);
        }
    }
}
