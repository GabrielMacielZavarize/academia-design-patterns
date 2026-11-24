package br.com.academia.builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.academia.model.Plano;


@Component
public class DiretorPlanos {

    public Plano criarPlanoMensal() {
        return new PlanoBuilder()
                .nome("Mensal")
                .valorMensal(new BigDecimal("99.90"))
                .duracaoMeses(1)
                .modalidade("Musculação")
                .horarioLivre(false)
                .build();
    }

    public Plano criarPlanoTrimestral() {
        return new PlanoBuilder()
                .nome("Trimestral")
                .valorMensal(new BigDecimal("89.90"))
                .duracaoMeses(3)
                .modalidade("Musculação")
                .modalidade("Spinning")
                .horarioLivre(false)
                .build();
    }

    public Plano criarPlanoAnual() {
        return new PlanoBuilder()
                .nome("Anual")
                .valorMensal(new BigDecimal("79.90"))
                .duracaoMeses(12)
                .modalidade("Musculação")
                .modalidade("Spinning")
                .modalidade("Funcional")
                .horarioLivre(true)
                .build();
    }

   
    public List<Plano> planosDisponiveis() {
        return List.of(
                criarPlanoMensal(),
                criarPlanoTrimestral(),
                criarPlanoAnual()
        );
    }

  
    public Optional<Plano> buscarPorNome(String nome) {
        return planosDisponiveis().stream()
                .filter(plano -> plano.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
}