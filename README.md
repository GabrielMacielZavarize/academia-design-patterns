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

- Problema resolvido: um `Plano` tem vários atributos (nome, valor mensal, duração, lista de modalidades e horário livre), alguns obrigatórios e outros opcionais. Um construtor com todos esses parâmetros seria difícil de ler e fácil de errar (por exemplo, trocar a ordem de valores do mesmo tipo). Além disso, queremos garantir que nenhum plano inválido seja criado. O Builder separa a construção do objeto da sua representação final, montando o plano passo a passo e validando tudo antes de entregá-lo.
### Classes envolvidas:

- **Produto** – `Plano` (`model`): objeto final e imutável. Só é criado a partir de um PlanoBuilder.
- **Builder** – `PlanoBuilder` (`builder`): interface fluente (`nome()`, `valorMensal()`, `duracaoMeses()`, `modalidade()`, `horarioLivre()`) que acumula os valores. O método `build()` valida as regras e cria o `Plano`.
- **Diretor** – `DiretorPlanos` (`builder`): guarda as "receitas" dos planos (Mensal, Trimestral e Anual) e concentra em `planosDisponiveis()` a lista de todos os planos oferecidos. É um bean do Spring, usado pelos services e controllers.

### Observer

- **Problema resolvido:** quando um aluno faz check-in, várias partes do sistema precisam reagir (confirmar ao aluno, avisar a recepção etc.). Se o `CheckInService` chamasse cada notificação diretamente, ele ficaria acoplado a todas elas e precisaria ser alterado a cada notificação nova. O Observer resolve isso: o serviço apenas avisa que um check-in aconteceu, e todos os observadores registrados são notificados automaticamente, sem que o serviço saiba quem eles são.

### Classes envolvidas:

- **Sujeito** – `PublicadorCheckIn` (`observer`): guarda a lista de observadores e tem os métodos `adicionar()`, `remover()` e `notificar()`. Recebe pelo construtor todos os beans que implementam `ObservadorCheckIn` e os registra.
- **Observador** – `ObservadorCheckIn` (`observer`): interface com o método `aoRegistrarCheckIn(CheckIn)`, contrato de toda notificação.
- **Observadores concretos** – `NotificacaoAluno` e `NotificacaoRecepcao` (`observer`): imprimem no console a confirmação para o aluno e o aviso para a recepção.
- **Quem dispara o evento** – `CheckInService` (`service`): após registrar o check-in, chama `publicadorCheckIn.notificar(checkIn)`. Conhece apenas o `PublicadorCheckIn`, nunca as notificações concretas.

Para adicionar uma nova notificação, basta criar uma nova classe `@Component` que implementa `ObservadorCheckIn`: ela é registrada automaticamente no `PublicadorCheckIn`, sem alterar o `CheckInService`.

## 6. Declaração de uso de IA

- **Modelo utilizado:** Claude Opus 5.5 (Anthropic)
- Gabriel Maciel: Criação e configuração do projeto (Spring Boot, Maven Wrapper, `.gitignore`), estrutura do README, auxiliar na criação da entidade `Aluno` e `AlunoService`
- Nicolas Machado (ChatGPT/Codex, OpenAI): Geração do código de `CheckIn`, `CheckInService`, controllers REST e tratamento de erros a partir das especificações do grupo.
- Pedro Harter: Auxiliar na criação do pattern Observer (`ObservadorCheckIn`, `PublicadorCheckIn`, `NotificacaoAluno` e `NotificacaoRecepcao`) e na integração com o `CheckInService`.

## 7. Contribuição de cada integrante

| Integrante | Contribuição |
|---|---|
| Gabriel Maciel | Criação do projeto, configuração inicial, README base e entidade `Aluno` |
| Nicolas Machado | Entidade `CheckIn`, `CheckInService`, controllers REST do fluxo completo, tratamento de erros e exemplos de uso |
| Pedro Harter | Pattern Observer: interface `ObservadorCheckIn`, sujeito `PublicadorCheckIn`, notificações `NotificacaoAluno` e `NotificacaoRecepcao` e integração com o `CheckInService` |
| Wilian Vieira | Pattern Builder: `Plano` e `PlanoBuilder` |
