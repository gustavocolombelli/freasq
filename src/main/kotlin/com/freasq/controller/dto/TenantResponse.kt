package com.freasq.controller.dto

import com.freasq.entity.Tenant
import java.util.UUID
import java.time.LocalDateTime

data class TenantResponse(
    val publicId: UUID,
    val name: String,
    val slug: String,
    val status: String, // Mantido como String para a resposta da API
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(entity: Tenant) = TenantResponse(
            publicId = entity.publicId,
            name = entity.name,
            slug = entity.slug,
            status = entity.status.name,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
