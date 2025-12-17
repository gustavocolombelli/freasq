# Guia de Design da API Freasq

**Versão: 1.0**

Este documento descreve as convenções, padrões e a estrutura geral para o desenvolvimento e consumo da API do Freasq. O objetivo é garantir consistência, previsibilidade e clareza em todos os endpoints.

## Estrutura de Resposta Padrão

Todas as respostas da API, sejam de sucesso ou de erro, são encapsuladas em um envelope JSON padrão. Isso simplifica o tratamento das respostas no lado do cliente.

A estrutura do envelope é a seguinte:

```json
{
  "success": true,
  "data": {},
  "error": null
}
```

*   `success` (boolean): Indica o resultado da operação. `true` para sucesso, `false` para erro.
*   `data` (object | null): Em caso de sucesso, contém os dados da resposta. É `null` em caso de erro.
*   `error` (object | null): Em caso de erro, contém um objeto `ApiError` detalhando o problema. É `null` em caso de sucesso.

### Exemplo de Resposta de Sucesso

**`GET /tenants/{publicId}`**
```json
{
    "success": true,
    "data": {
        "publicId": "a1b2c3d4-e5f6-7890-1234-567890abcdef",
        "name": "Meu Primeiro Tenant",
        "slug": "meu-primeiro-tenant",
        "status": "ACTIVE",
        "createdAt": "2025-12-17T10:00:00Z",
        "updatedAt": "2025-12-17T10:00:00Z"
    }
}
```

## Tratamento de Erros

Quando uma operação falha (`"success": false`), o campo `error` será preenchido com um objeto `ApiError`, que segue a estrutura abaixo:

```json
{
  "code": "CODIGO_DO_ERRO",
  "message": "Uma mensagem clara e legível para o erro.",
  "details": [ "Detalhe 1", "Detalhe 2" ]
}
```

*   `code` (string): Um identificador único e programático para o tipo de erro.
*   `message` (string): Uma mensagem de alto nível que resume o problema.
*   `details` (array<object> | null): Uma lista opcional com objetos estruturados detalhando cada erro, usada principalmente para validação.

### Estratégia de Erros

*   **Erro Único (Regra de Negócio):** O campo `message` contém a descrição completa e específica do erro. O campo `details` é omitido.
*   **Múltiplos Erros (Validação de Dados):** O campo `message` contém um resumo genérico (ex: "A requisição possui campos inválidos."), e o campo `details` contém a lista de todos os problemas encontrados.

### Escolha dos Status HTTP de Erro

A escolha do código de status HTTP para erros é crucial para uma API semântica. A seguir, nossa estratégia:

*   **`400 Bad Request`**: Usado para **erros de sintaxe ou de contrato**. A requisição do cliente está malformada e não pode ser entendida pelo servidor (ex: JSON inválido, campos obrigatórios faltando, formato de dados incorreto). A falha ocorre antes do processamento da lógica de negócio.

*   **`409 Conflict`**: Usado quando a requisição é sintaticamente perfeita, mas entra em **conflito com o estado atual do servidor**. O principal caso de uso é a violação de uma restrição de unicidade (ex: tentar criar um recurso com um `slug` que já existe). O cliente pode, teoricamente, resolver o conflito e tentar novamente.

*   **`422 Unprocessable Entity`**: Reservado para futuras **violações de regras de negócio semânticas** que não são um conflito de estado direto. O servidor entende a requisição, mas se recusa a processá-la por uma razão de negócio (ex: "um usuário do plano gratuito não pode criar mais de 10 projetos").

*   **`404 Not Found`**: Usado quando o recurso-alvo de uma requisição (identificado por um ID na URL, por exemplo) não existe.

### Exemplos de Respostas de Erro

#### Erro de Validação (HTTP 400 Bad Request)
Ocorre quando os dados enviados na requisição não atendem aos critérios de **formato ou contrato** (ex: campos vazios, tamanho excedido, tipo de dado incorreto).

```json
{
    "success": false,
    "error": {
        "code": "VALIDATION_ERROR",
        "message": "A requisição possui campos inválidos.",
        "details": [
            { "field": "name", "message": "O nome não pode ser vazio" },
            { "field": "slug", "message": "O slug não pode exceder 100 caracteres" }
        ]
    }
}
```

#### Erro de Conflito (HTTP 409 Conflict)
Ocorre quando a requisição é válida, mas não pode ser completada devido a um conflito com o estado atual do recurso (ex: tentar criar um recurso que já existe).

```json
{
    "success": false,
    "error": {
        "code": "BUSINESS_RULE_VIOLATION",
        "message": "O slug 'meu-tenant' já está em uso."
    }
}
```

#### Recurso Não Encontrado (HTTP 404 Not Found)
Ocorre ao tentar acessar um recurso que não existe.

```json
{
    "success": false,
    "error": {
        "code": "RESOURCE_NOT_FOUND",
        "message": "Tenant com o ID 'a1b2c3d4-e5f6-7890-1234-567890abcdef' não foi encontrado."
    }
}
```