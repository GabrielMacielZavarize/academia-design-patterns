package br.com.academia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academia.builder.DiretorPlanos;
import br.com.academia.model.Plano;

@RestController
public class PlanoController {

    private final DiretorPlanos diretorPlanos;

    public PlanoController(DiretorPlanos diretorPlanos) {
        this.diretorPlanos = diretorPlanos;
    }

    @GetMapping("/planos")
    public List<Plano> listarPlanos() {
        // O diretor reúne os planos disponíveis.
        return diretorPlanos.planosDisponiveis();
    }
}
