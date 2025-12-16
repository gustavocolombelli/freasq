package com.freasq.infrastructure.persistence.tenant

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TenantJpaRepository : JpaRepository<TenantEntity, Long> {

    fun existsBySlug(slug: String): Boolean

    fun findByPublicId(publicId: UUID): TenantEntity?
}
