package com.freasq.domain.tenant.repository

import com.freasq.domain.tenant.model.Tenant
import java.util.UUID

interface TenantRepository{
    fun save(tenant: Tenant): Tenant
    fun existsBySlug(slug: String): Boolean
    fun findByPublicId(publicId: UUID): Tenant?
}