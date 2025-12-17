package com.freasq.controller.handler

import com.freasq.controller.response.ApiResponse
import com.freasq.controller.response.ErrorCode
import com.freasq.controller.response.ValidationErrorDetail
import com.freasq.exception.TenantNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * Trata erros de validação de DTOs anotados com @Valid.
     * Retorna HTTP 400 (Bad Request) com uma lista de campos inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {
        val errors = ex.bindingResult.fieldErrors.map {
            ValidationErrorDetail(it.field, it.defaultMessage ?: "Erro de validação")
        }
        log.warn("Validation error: {}", errors)
        val response = ApiResponse.error<Unit>(
            code = ErrorCode.VALIDATION_ERROR,
            message = "A requisição possui campos inválidos.",
            details = errors
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * Trata erros de parsing do corpo da requisição (ex: JSON malformado).
     * Retorna HTTP 400 (Bad Request).
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMalformedJson(ex: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("Malformed JSON request: {}", ex.message)
        val response = ApiResponse.error<Unit>(
            code = ErrorCode.VALIDATION_ERROR,
            message = "O corpo da requisição está malformado ou é inválido."
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    /**
     * Trata o caso em que um recurso esperado não foi encontrado.
     * Retorna HTTP 404 (Not Found).
     */
    @ExceptionHandler(TenantNotFoundException::class)
    fun handleNotFound(ex: TenantNotFoundException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("Resource not found: {}", ex.message)
        val response = ApiResponse.error<Unit>(ErrorCode.RESOURCE_NOT_FOUND, ex.message ?: "Recurso não encontrado.")
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    /**
     * Trata violações de regras de negócio, como argumentos inválidos ou duplicados.
     * Retorna HTTP 409 (Conflict) para indicar um conflito com o estado atual do recurso.
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBusinessRule(ex: IllegalArgumentException): ResponseEntity<ApiResponse<Unit>> {
        log.warn("Business rule violation: {}", ex.message)
        val response = ApiResponse.error<Unit>(ErrorCode.BUSINESS_RULE_VIOLATION, ex.message ?: "Violação de regra de negócio.")
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }

    /**
     * Trata qualquer erro inesperado não capturado pelos handlers específicos.
     * Retorna HTTP 500 (Internal Server Error) para evitar expor detalhes internos.
     */
    @ExceptionHandler(Exception::class)
    fun handleUnexpected(ex: Exception): ResponseEntity<ApiResponse<Unit>> {
        log.error("Unexpected error", ex)
        val response = ApiResponse.error<Unit>(ErrorCode.INTERNAL_SERVER_ERROR, "Ocorreu um erro interno inesperado.")
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
