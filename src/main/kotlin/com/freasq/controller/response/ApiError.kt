package com.freasq.controller.response

/**
 * Estrutura padronizada para detalhar um erro na API.
 *
 * ### Estratégia de Uso:
 * - **Erro Único (Ex: Regra de Negócio):**
 *   - `message`: Contém a descrição específica e completa do erro.
 *   - `details`: Geralmente é `null`.
 * - **Múltiplos Erros (Ex: Validação de Formulário):**
 *   - `message`: Contém uma mensagem genérica (ex: "A requisição possui campos inválidos.").
 *   - `details`: Contém a lista com cada erro de campo específico.
 *
 * @property code Um código de erro único para identificação programática.
 * @property message Uma mensagem de erro legível para humanos.
 * @property details Uma lista opcional de erros mais específicos, útil para validação de múltiplos campos.
 */
data class ApiError(val code: ErrorCode, val message: String, val details: Any? = null)