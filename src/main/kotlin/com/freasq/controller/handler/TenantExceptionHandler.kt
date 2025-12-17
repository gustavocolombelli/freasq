package com.freasq.controller.handler

import com.freasq.controller.response.ApiResponse
import com.freasq.controller.response.ErrorCode
import com.freasq.exception.ResourceNotFoundException
import com.freasq.exception.SlugAlreadyExistsException
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(1) // Garante que este handler específico seja consultado antes do global
class TenantExceptionHandler : BaseExceptionHandler() {
    private val log = LoggerFactory.getLogger(TenantExceptionHandler::class.java)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<ApiResponse<Any>> {
        log.warn("Resource not found: {}", ex.message)
        return createErrorResponse(ErrorCode.RESOURCE_NOT_FOUND, ex.message ?: "Recurso não encontrado.")
    }

    @ExceptionHandler(SlugAlreadyExistsException::class)
    fun handleBusinessRule(ex: SlugAlreadyExistsException): ResponseEntity<ApiResponse<Any>> {
        log.warn("Business rule violation: {}", ex.message)
        return createErrorResponse(ErrorCode.BUSINESS_RULE_VIOLATION, ex.message ?: "Violação de regra de negócio.")
    }
}