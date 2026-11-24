package br.com.academia.model;

import br.com.academia.builder.PlanoBuilder;

import java.math.BigDecimal;
import java.util.List;

// PRODUTO do Builder: o objeto final e complexo que o Builder monta.
// É imutável: tudo é final e não existem setters.
public class Plano {

    private final String nome;
    private final BigDecimal valorMensal;
    private final int duracaoMeses;
    private final List<String> modalidades;
    private final boolean horarioLivre;

    // Construtor público (pacotes diferentes), mas só aceita um PlanoBuilder:
    // na prática, o Plano só nasce a partir do Builder.
    public Plano(PlanoBuilder builder) {
        this.nome = builder.getNome();
        this.valorMensal = builder.getValorMensal();
        this.duracaoMeses = builder.getDuracaoMeses();
        // Cópia imutável: mexer na lista do Builder depois não altera o Plano
        this.modalidades = List.copyOf(builder.getModalidades());
        this.horarioLivre = builder.isHorarioLivre();
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    public int getDuracaoMeses() {
        return duracaoMeses;
    }

    public List<String> getModalidades() {
        return modalidades;
    }

    public boolean isHorarioLivre() {
        return horarioLivre;
    }

    @Override
    public String toString() {
        return "Plano{nome='" + nome + "', valorMensal=" + valorMensal
                + ", duracaoMeses=" + duracaoMeses
                + ", modalidades=" + modalidades
                + ", horarioLivre=" + horarioLivre + "}";
    }
}