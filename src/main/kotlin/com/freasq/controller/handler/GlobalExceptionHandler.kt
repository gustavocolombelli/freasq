package com.freasq.controller.handler

import com.freasq.controller.response.ApiResponse
import com.freasq.controller.response.ErrorCode
import com.freasq.controller.response.ValidationErrorDetail
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(10) // Ordem menor, para ser consultado depois dos handlers específicos
class GlobalExceptionHandler : BaseExceptionHandler() {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * Trata erros de validação de DTOs anotados com @Valid.
     * Retorna HTTP 400 (Bad Request) com uma lista de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Any>> {
        val errors = ex.bindingResult.fieldErrors.map {
            ValidationErrorDetail(it.field, it.defaultMessage ?: "Erro de validação")
        }
        log.warn("Validation error: {}", errors)
        return createErrorResponse(ErrorCode.VALIDATION_ERROR, "A requisição possui campos inválidos.", errors)
    }

    /**
     * Trata erros de parsing do corpo da requisição (ex: JSON malformado).
     * Retorna HTTP 400 (Bad Request).
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMalformedJson(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Any>> {
        log.warn("Malformed JSON request: {}", ex.message)
        return createErrorResponse(ErrorCode.VALIDATION_ERROR, "O corpo da requisição está malformado ou é inválido.")
    }

    /**
     * Trata qualquer erro inesperado não capturado pelos handlers específicos.
     * Retorna HTTP 500 (Internal Server Error) para evitar expor detalhes internos.
     */
    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: Exception): ResponseEntity<ApiResponse<Any>> {
        log.error("Unexpected error", ex)
        return createErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno inesperado.")
    }
}
