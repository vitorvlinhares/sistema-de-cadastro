# Sistema de Cadastro

Aplicação desktop em JavaFX para cadastro de funcionários (nome, cargo, salário e dependentes), com persistência simples em arquivo JSON (`database.json`).

## Requisitos

- Java 25 (JDK) — o projeto usa Maven Toolchains para localizar a JDK 25 automaticamente; configure-a em `~/.m2/toolchains.xml`
- Maven 3.9+

## Como rodar

```bash
mvn clean javafx:run
```

## Como empacotar

```bash
mvn clean package
```

Gera o jar em `target/AV3-FINAL-1.0-SNAPSHOT.jar`.

## Estrutura

- `src/main/java/app` — código-fonte (classe principal `app.App`, controllers JavaFX, camada `data_base` de leitura/escrita do JSON)
- `src/main/resources/app` — telas FXML
- `database.json` — arquivo de dados (criado/atualizado em tempo de execução)
