package br.com.academia.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.academia.model.Plano;

public class PlanoBuilder {

    private String nome;
    private BigDecimal valorMensal;
    private int duracaoMeses;
    private final List<String> modalidades = new ArrayList<>();
    private boolean horarioLivre = false; // opcional: padrão é false

    public PlanoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public PlanoBuilder valorMensal(BigDecimal valorMensal) {
        this.valorMensal = valorMensal;
        return this;
    }

    public PlanoBuilder duracaoMeses(int duracaoMeses) {
        this.duracaoMeses = duracaoMeses;
        return this;
    }

    
    public PlanoBuilder modalidade(String modalidade) {
        this.modalidades.add(modalidade);
        return this;
    }

    public PlanoBuilder horarioLivre(boolean horarioLivre) {
        this.horarioLivre = horarioLivre;
        return this;
    }

    
    public Plano build() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalStateException("O nome do plano é obrigatório.");
        }
        if (valorMensal == null || valorMensal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("O valor mensal deve ser maior que zero.");
        }
        if (duracaoMeses < 1) {
            throw new IllegalStateException("A duração do plano deve ser de pelo menos 1 mês.");
        }
        if (modalidades.isEmpty()) {
            throw new IllegalStateException("O plano deve ter pelo menos uma modalidade.");
        }
        return new Plano(this);
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
}