package br.com.academia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.academia.model.CheckIn;
import br.com.academia.service.CheckInService;

@RestController
public class CheckInController {

    private final CheckInService checkInService;

    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }

    @PostMapping("/alunos/{id}/checkins")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckIn registrar(@PathVariable("id") Long alunoId) {
        // O ID do aluno vem da URL.
        return checkInService.registrar(alunoId);
    }

    @GetMapping("/checkins")
    public List<CheckIn> listarCheckIns() {
        return checkInService.listarTodos();
    }
}
