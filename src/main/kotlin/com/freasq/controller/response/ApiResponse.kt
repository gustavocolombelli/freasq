package com.freasq.controller.response

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Envelope padrão para todas as respostas da API.
 *
 * Esta classe garante uma estrutura de resposta consistente em toda a aplicação.
 * A construção de instâncias é forçada através dos métodos de fábrica do `companion object`
 * para garantir que uma resposta seja sempre de sucesso (com dados) ou de erro (com um erro), nunca ambos.
 * @param T O tipo do dado encapsulado na resposta em caso de sucesso.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiResponse<T> private constructor(
    val success: Boolean,
    val data: T? = null,
    val error: ApiError? = null
) {
    companion object {
        /**
         * Cria uma resposta de API bem-sucedida.
         */
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(success = true, data = data, error = null)
        }

        /**
         * Cria uma resposta de API de erro.
         * @param code O código de erro programático.
         * @param message A mensagem principal que resume o erro.
         * @param details Uma lista opcional com detalhes específicos do erro.
         */
        fun <T> error(code: ErrorCode, message: String, details: Any? = null): ApiResponse<T> {
            return ApiResponse(
                success = false,
                data = null,
                error = ApiError(code, message, details)
            )
        }
    }
}
