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

Consulte os planos, matricule um aluno e registre o check-in para disparar as notificações.

Com a aplicação rodando na porta 8080, abra outro terminal e siga os passos em ordem. Escolha **um comando por passo**, PowerShell ou curl. No Linux ou macOS, use `curl` no lugar de `curl.exe`.

**Dados em memória.** Ao reiniciar a aplicação, as matrículas e os check-ins são apagados. Comece com uma aplicação recém-iniciada para que Ana receba o ID `1`.

As respostas abaixo resumem o JSON da API. O PowerShell 5.1 pode exibir listas com os campos `value` e `Count` e mostrar alguns acentos incorretamente. O `curl.exe -i` exibe o status HTTP e o corpo da resposta.

### 1. Listar planos

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/planos' | ConvertTo-Json -Depth 5
```

```sh
curl.exe -sS -i 'http://localhost:8080/planos'
```

**200 OK.** A lista contém os três planos criados pelo Builder.

| Plano | Valor mensal (R$) | Duração | Modalidades | Horário livre |
|---|---:|---:|---|---|
| Mensal | 99,90 | 1 mês | Musculação | Não |
| Trimestral | 89,90 | 3 meses | Musculação, Spinning | Não |
| Anual | 79,90 | 12 meses | Musculação, Spinning, Funcional | Sim |

### 2. Matricular Ana Souza no plano Mensal

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/alunos' -Method Post `
    -ContentType 'application/json' `
    -Body '{"nome":"Ana Souza","email":"ana@example.com","plano":"Mensal"}' |
    ConvertTo-Json -Depth 5
```

```sh
echo '{"nome":"Ana Souza","email":"ana@example.com","plano":"Mensal"}' | curl.exe -sS -i -H 'Content-Type: application/json' --data-binary '@-' 'http://localhost:8080/alunos'
```

**201 Created.** A resposta traz a aluna com o ID `1` e os dados completos do plano.

```json
{
  "id": 1,
  "nome": "Ana Souza",
  "email": "ana@example.com",
  "plano": {
    "nome": "Mensal",
    "valorMensal": 99.90,
    "duracaoMeses": 1,
    "modalidades": ["Musculação"],
    "horarioLivre": false
  }
}
```

### 3. Listar alunos

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/alunos' | ConvertTo-Json -Depth 5
```

```sh
curl.exe -sS -i 'http://localhost:8080/alunos'
```

**200 OK.** A lista contém uma aluna, Ana Souza (`id: 1`), com o plano Mensal.

### 4. Registrar o check-in da aluna 1

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/alunos/1/checkins' -Method Post |
    ConvertTo-Json -Depth 5
```

```sh
curl.exe -sS -i -X POST 'http://localhost:8080/alunos/1/checkins'
```

**201 Created.** A resposta traz o check-in com `id: 1`, o objeto `aluno` completo e `dataHora`. No teste, `dataHora` foi `2026-11-24T21:44:56.5042569`.

**Saída no terminal da aplicação.** Assim que o check-in é salvo, o Observer aciona as notificações do aluno e da recepção. O horário acompanha o momento do registro.

```text
[Aluno] Olá, Ana Souza! Check-in confirmado às 21:44. Bom treino!
[Recepção] Ana Souza (Plano Mensal) entrou às 21:44.
```

### 5. Listar check-ins

```powershell
Invoke-RestMethod -Uri 'http://localhost:8080/checkins' | ConvertTo-Json -Depth 5
```

```sh
curl.exe -sS -i 'http://localhost:8080/checkins'
```

**200 OK.** A lista contém o check-in `1` de Ana Souza, com a mesma `dataHora` retornada no passo anterior.

### 6. Tentar matricular em um plano inexistente

No PowerShell 5.1, o `catch` mostra o status HTTP e o corpo JSON da resposta de erro.

```powershell
try {
    Invoke-RestMethod -Uri 'http://localhost:8080/alunos' -Method Post `
        -ContentType 'application/json' `
        -Body '{"nome":"Bruno Lima","email":"bruno@example.com","plano":"Premium"}' `
        -ErrorAction Stop
} catch {
    [int]$_.Exception.Response.StatusCode
    $_.ErrorDetails.Message
}
```

```sh
echo '{"nome":"Bruno Lima","email":"bruno@example.com","plano":"Premium"}' | curl.exe -sS -i -H 'Content-Type: application/json' --data-binary '@-' 'http://localhost:8080/alunos'
```

**400 Bad Request.**

```json
{"erro":"Plano não encontrado: Premium"}
```

### 7. Tentar registrar check-in de um aluno inexistente

```powershell
try {
    Invoke-RestMethod -Uri 'http://localhost:8080/alunos/99/checkins' -Method Post -ErrorAction Stop
} catch {
    [int]$_.Exception.Response.StatusCode
    $_.ErrorDetails.Message
}
```

```sh
curl.exe -sS -i -X POST 'http://localhost:8080/alunos/99/checkins'
```

**400 Bad Request.**

```json
{"erro":"Aluno não encontrado: 99"}
```

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
