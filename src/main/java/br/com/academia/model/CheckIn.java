package br.com.academia.model;

import java.time.LocalDateTime;

/** Registra a entrada de um aluno em um horário, com dados imutáveis. */
public class CheckIn {

    private final Long id;
    private final Aluno aluno;
    private final LocalDateTime dataHora;

    public CheckIn(Long id, Aluno aluno, LocalDateTime dataHora) {
        this.id = id;
        this.aluno = aluno;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        return "CheckIn{id=" + id + ", alunoId=" + aluno.getId() + ", dataHora=" + dataHora + "}";
    }
}
