package br.com.academia.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.academia.builder.DiretorPlanos;
import br.com.academia.model.Aluno;
import br.com.academia.model.Plano;

/**
 * Regras de matrícula e consulta de alunos.
 * Os dados ficam em memória (sem banco de dados) enquanto a aplicação está rodando.
 */
@Service
public class AlunoService {

    private final DiretorPlanos diretorPlanos;

    // LinkedHashMap mantém os alunos na ordem de cadastro
    private final Map<Long, Aluno> alunos = new LinkedHashMap<>();

    // Gera ids sequenciais: 1, 2, 3...
    private final AtomicLong proximoId = new AtomicLong(1);

    // Injeção pelo construtor: o Spring entrega o DiretorPlanos pronto
    public AlunoService(DiretorPlanos diretorPlanos) {
        this.diretorPlanos = diretorPlanos;
    }

    public Aluno matricular(String nome, String email, String nomePlano) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail do aluno é obrigatório.");
        }

        // Não cria plano: pega um plano pronto do DiretorPlanos (Builder)
        Plano plano = diretorPlanos.buscarPorNome(nomePlano)
                .orElseThrow(() -> new IllegalArgumentException("Plano não encontrado: " + nomePlano));

        Aluno aluno = new Aluno(proximoId.getAndIncrement(), nome, email, plano);
        alunos.put(aluno.getId(), aluno);
        return aluno;
    }

    public List<Aluno> listarTodos() {
        // Cópia imutável: quem chama não consegue alterar a lista interna
        return List.copyOf(alunos.values());
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return Optional.ofNullable(alunos.get(id));
    }
}
