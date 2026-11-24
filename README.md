# Projeto Final de Design Patterns

> Aplicação Java (Spring Boot) para gestão simples de uma academia, aplicando os patterns **Builder** e **Observer**.

## 1. Descrição do sistema

Sistema para cadastrar **planos**, matricular **alunos** e registrar **check-ins**. A cada check-in, as notificações registradas são disparadas.

**Entidades principais:** `Aluno`, `Plano`, `CheckIn`.

## 2. Tecnologias e pré-requisitos

- Java 21 (Eclipse Temurin)
- Spring Boot 4.1.x
- Maven (via Maven Wrapper incluído no projeto não é necessário instalar o Maven)
- Sem banco de dados: os dados ficam em memória enquanto a aplicação está rodando.

**Pré-requisito:** ter o Java 21 instalado e a variável `JAVA_HOME` configurada.

## 3. Instruções de execução

No terminal (PowerShell), dentro da pasta do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

## 4. Exemplos de uso

_(A preencher quando os endpoints estiverem prontos)_

## 5. Patterns utilizados

### Builder
- **Problema resolvido:** _(a preencher)_
- **Classes envolvidas:** _(a preencher)_

### Observer
- **Problema resolvido:** _(a preencher)_
- **Classes envolvidas:** _(a preencher)_

## 6. Declaração de uso de IA

- **Modelo utilizado:** Claude Opus 5.5 (Anthropic)
- **Partes em que foi utilizado:** _(a preencher ao final — ex.: planejamento da arquitetura, orientação na configuração do ambiente, revisão do README, ...)_

## 7. Contribuição de cada integrante

| Integrante | Contribuição |
|---|---|
| Gabriel Maciel | Criação do projeto, configuração inicial, README base e entidade `Aluno` |
| Nicolas Machado | Entidade `CheckIn`, fluxo completo (endpoints) e exemplos de uso|
| Pedro Harter | Pattern Observer: interface de notificação e notificações concretas |
| Wilian Vieira | Pattern Builder: `Plano` e `PlanoBuilder` |