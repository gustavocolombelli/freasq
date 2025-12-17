package com.freasq.controller.handler

import com.freasq.controller.response.ApiResponse
import com.freasq.controller.response.ErrorCode
import org.springframework.http.ResponseEntity

/**
 * Classe base para os handlers de exceção, contendo a lógica comum
 * para criar uma resposta de erro padronizada.
 */
abstract class BaseExceptionHandler {
    protected fun createErrorResponse(errorCode: ErrorCode, message: String, details: Any? = null): ResponseEntity<ApiResponse<Any>> {
        val apiResponse = ApiResponse.error<Any>(errorCode, message, details)
        return ResponseEntity(apiResponse, errorCode.status)
    }
}