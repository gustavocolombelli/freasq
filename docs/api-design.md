# Guia de Design da API Freasq

**Versão: 1.0**

Este documento descreve as convenções, padrões e a estrutura para o desenvolvimento e consumo da API do Freasq. O objetivo é garantir consistência, previsibilidade e clareza em todos os endpoints.

## Estrutura de Resposta Padrão

Todas as respostas da API, sejam de sucesso ou de erro, são encapsuladas num envelope JSON padrão. Isso simplifica o tratamento das respostas no lado do cliente.

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

Quando uma operação falha (`"success": false`), o campo `error` será preenchido com um objeto `ApiError`.

### Estrutura do Objeto de Erro

```json
{
  "code": "UM_CODIGO_DE_ERRO",
  "message": "Uma mensagem clara e legível para o erro.",
  "details": [
      { "field": "nomeDoCampo", "message": "Descrição do erro para este campo." }
  ]
}
```

*   `code` (string): Um identificador programático para o tipo de erro.
*   `message` (string): Uma mensagem de alto nível que resume o problema.
*   `details` (array<object> | null): **Campo opcional.** Usado para fornecer uma lista estruturada de suberros, principalmente em falhas de validação. O formato de cada objeto é `{"field": "nomeDoCampo", "message": "mensagemDeErro"}`.

### Códigos de Erro e Status HTTP

Cada `code` de erro está diretamente associado a um status HTTP específico. Isso garante que a API seja previsível: um mesmo código de erro sempre retornará o mesmo status HTTP.

A seguir, a lista de códigos e seus respectivos status:

*   `VALIDATION_ERROR` -> **`400 Bad Request`**
    *   **O que significa**: A requisição está malformada ou viola o contrato da API (ex: JSON inválido, campos obrigatórios faltando, formato de dados incorreto).

*   `BUSINESS_RULE_VIOLATION` -> **`409 Conflict`**
    *   **O que significa**: A requisição é válida, mas entra em conflito com o estado atual do servidor (ex: tentar criar um recurso com um `slug` que já existe).

*   `RESOURCE_NOT_FOUND` -> **`404 Not Found`**
    *   **O que significa**: O recurso específico que você tentou acessar não existe.

*   `INTERNAL_SERVER_ERROR` -> **`500 Internal Server Error`**
    *   **O que significa**: Um erro inesperado e não tratado ocorreu no servidor.

### Exemplos de Respostas de Erro

#### Erro de Validação de Campos (HTTP 400)
Ocorre quando os dados enviados não atendem aos critérios de formato. O campo `details` é preenchido.

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

#### Erro de Sintaxe da Requisição (HTTP 400)
Ocorre quando a requisição em si está malformada (ex: JSON inválido). O campo `details` é omitido.

```json
{
    "success": false,
    "error": {
        "code": "VALIDATION_ERROR",
        "message": "O corpo da requisição está malformado ou é inválido."
    }
}
```

#### Erro de Conflito (HTTP 409)
Ocorre ao tentar criar um recurso com um identificador único que já existe.

```json
{
    "success": false,
    "error": {
        "code": "BUSINESS_RULE_VIOLATION",
        "message": "O slug 'meu-tenant' já está em uso."
    }
}
```

#### Recurso Não Encontrado (HTTP 404)
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