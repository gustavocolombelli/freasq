package com.freasq.application.tenant.dto

import com.freasq.domain.tenant.model.Tenant
import java.util.UUID

// application/tenant/dto/TenantResponse.kt
data class TenantResponse(
    val id: UUID,
    val name: String,
    val slug: String,
    val status: String
) {
    companion object {
        fun from(tenant: Tenant) =
            TenantResponse(
                id = tenant.publicId,
                name = tenant.name,
                slug = tenant.slug,
                status = tenant.status.name
            )
    }
}
