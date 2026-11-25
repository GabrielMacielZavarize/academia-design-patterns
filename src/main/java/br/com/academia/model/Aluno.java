package br.com.academia.model;

/**
 * Aluno matriculado na academia.
 * Classe simples e imutável: os dados são definidos no construtor e não mudam depois.
 */
public class Aluno {

    private final Long id;
    private final String nome;
    private final String email;
    // Plano pronto, criado pelo DiretorPlanos (pattern Builder)
    private final Plano plano;

    public Aluno(Long id, String nome, String email, Plano plano) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.plano = plano;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Plano getPlano() {
        return plano;
    }

    @Override
    public String toString() {
        return "Aluno{id=" + id + ", nome='" + nome + "', email='" + email
                + "', plano=" + plano.getNome() + "}";
    }
}
