package br.com.academia.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.academia.model.Aluno;
import br.com.academia.model.CheckIn;

@Service
public class CheckInService {

    private final AlunoService alunoService;
    private final List<CheckIn> checkIns = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public CheckInService(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    public CheckIn registrar(Long alunoId) {
        Aluno aluno = alunoService.buscarPorId(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + alunoId));

        CheckIn checkIn;
        // Protege a lista durante acessos simultâneos.
        synchronized (checkIns) {
            checkIn = new CheckIn(proximoId.getAndIncrement(), aluno, LocalDateTime.now());
            checkIns.add(checkIn);
        }
        // OBSERVER (Pedro): notificar os observadores aqui
        return checkIn;
    }

    public List<CheckIn> listarTodos() {
        synchronized (checkIns) {
            return List.copyOf(checkIns);
        }
    }
}
