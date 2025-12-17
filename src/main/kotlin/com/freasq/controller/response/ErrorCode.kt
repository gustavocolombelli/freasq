package com.freasq.controller.response

import org.springframework.http.HttpStatus

/**
 * Enum para padronizar os códigos de erro da API.
 * Isso garante consistência e evita erros de digitação.
 * Cada código de erro está diretamente atrelado ao seu HttpStatus correspondente.
 */
enum class ErrorCode(val status: HttpStatus) {
    // Erro genérico para validação de dados de entrada (HTTP 400)
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),

    // Erro de regra de negócio ou conflito de estado (HTTP 409)
    BUSINESS_RULE_VIOLATION(HttpStatus.CONFLICT),

    // Erro para recurso não encontrado (HTTP 404)
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND),

    // Erro interno e inesperado do servidor (HTTP 500)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR)
}