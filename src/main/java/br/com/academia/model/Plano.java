package br.com.academia.model;

import java.math.BigDecimal;
import java.util.List;

import br.com.academia.builder.PlanoBuilder;


public class Plano {

    private final String nome;
    private final BigDecimal valorMensal;
    private final int duracaoMeses;
    private final List<String> modalidades;
    private final boolean horarioLivre;

   
    public Plano(PlanoBuilder builder) {
        this.nome = builder.getNome();
        this.valorMensal = builder.getValorMensal();
        this.duracaoMeses = builder.getDuracaoMeses();
        
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