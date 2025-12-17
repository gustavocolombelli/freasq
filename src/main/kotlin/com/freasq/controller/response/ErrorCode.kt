package com.freasq.controller.response

/**
 * Enum para padronizar os códigos de erro da API.
 * Isso garante consistência e evita erros de digitação.
 */
enum class ErrorCode {
    // Erro genérico para validação de dados de entrada (HTTP 400)
    VALIDATION_ERROR,

    // Erro de regra de negócio ou conflito de estado (HTTP 409)
    BUSINESS_RULE_VIOLATION,

    // Erro para recurso não encontrado (HTTP 404)
    RESOURCE_NOT_FOUND,

    // Erro interno e inesperado do servidor (HTTP 500)
    INTERNAL_SERVER_ERROR
}