# Freasq - Core Service de FAQ

Este projeto fornece um core service de FAQ no modelo SaaS, que posteriormente irá ser criado um front-end Backoffice (React) para consumir esta API.
O objetivo é gerar uma solução que permita a criação e manutenção de perguntas de FAQ via backoffice para oferecer uma API que será consumida a partir de um script, que é gerado dinâmicamente com base nas preferências do usuário, em que o script será executado dentro do serviço frontend desejado. Também governança de conteúdo, rastreabilidade de mudanças e controle operacional por organização (tenant).

## Documentação

Toda a documentação técnica e de design da API pode ser encontrada em nosso guia dedicado.

➡️ **[Guia de Design da API](./docs/api-design.md)**

## Como Executar

1.  Certifique-se de ter o Java 17 e o PostgreSQL rodando.
2.  Configure as variáveis de banco de dados em `src/main/resources/application.properties`.
3.  Execute a aplicação através da sua IDE ou usando o comando `./gradlew bootRun`.