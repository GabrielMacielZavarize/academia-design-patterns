package br.com.academia.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> tratarArgumentoInvalido(IllegalArgumentException excecao) {
        // O Spring serializa o mapa como JSON com a chave "erro".
        return Map.of("erro", excecao.getMessage());
    }
}
