package com.freasq.controller.response

/**
 * Representa o detalhe estruturado de um único erro de validação de campo.
 * @property field O nome do campo que falhou na validação.
 * @property message A mensagem de erro específica para este campo.
 */
data class ValidationErrorDetail(val field: String, val message: String)