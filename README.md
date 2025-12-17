# Freasq - Core Service de FAQ

Este projeto fornece um core service de FAQ no modelo SaaS, que pode ser integrado facilmente a diferentes aplicações (web, mobile ou sistemas internos).

O objetivo é garantir governança de conteúdo, rastreabilidade de mudanças e controle operacional por organização (tenant).

## Documentação

Toda a documentação técnica e de design da API pode ser encontrada em nosso guia dedicado.

➡️ **[Guia de Design da API](./docs/api-design.md)**

## Como Executar

### Pré-requisitos
*   Java 17 (ou superior)
*   PostgreSQL 15 (ou superior)

### Configuração

A aplicação é configurada para usar variáveis de ambiente, com valores padrão para facilitar o desenvolvimento local:

*   `DB_URL`: URL de conexão com o banco. (Padrão: `jdbc:postgresql://localhost:5432/freasq`)
*   `DB_USER`: Usuário do banco. (Padrão: `postgres`)
*   `DB_PASSWORD`: Senha do banco. (Padrão: `admin`)

### Executando Localmente

1.  **Banco de Dados**: Certifique-se de que seu PostgreSQL está rodando e que você criou um banco de dados chamado `freasq`.
2.  **Execução**:
    *   **Com os padrões**: Se seu ambiente local corresponde aos valores padrão, basta executar a aplicação com `./gradlew bootRun`.
    *   **Com variáveis customizadas**: Se seu ambiente é diferente, configure as variáveis de ambiente (`DB_URL`, `DB_USER`, `DB_PASSWORD`) no seu sistema operacional ou no seu ambiente de execução (IDE) antes de iniciar a aplicação.