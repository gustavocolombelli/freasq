package com.freasq.exception

/**
 * Exceção de base para todos os erros de "recurso não encontrado".
 */
open class ResourceNotFoundException(message: String) : RuntimeException(message)