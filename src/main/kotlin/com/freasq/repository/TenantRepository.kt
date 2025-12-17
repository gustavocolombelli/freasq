package com.freasq.repository

import com.freasq.entity.Tenant
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TenantRepository : JpaRepository<Tenant, Long> {

    fun findByPublicId(publicId: UUID): Tenant?

    fun existsBySlug(slug: String): Boolean
}
