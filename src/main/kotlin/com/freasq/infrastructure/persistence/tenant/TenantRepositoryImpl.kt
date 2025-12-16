package com.freasq.infrastructure.persistence.tenant

import com.freasq.domain.tenant.model.Tenant
import com.freasq.domain.tenant.repository.TenantRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TenantRepositoryImpl(
    private val jpaRepository: TenantJpaRepository
) : TenantRepository {

    override fun save(tenant: Tenant): Tenant {
        val entity = TenantEntity.fromDomain(tenant)
        val saved = jpaRepository.save(entity)
        return saved.toDomain()
    }

    override fun existsBySlug(slug: String): Boolean =
        jpaRepository.existsBySlug(slug)

    override fun findByPublicId(publicId: UUID): Tenant? {
        val entity = jpaRepository.findByPublicId(publicId)
        return entity?.toDomain()
    }
}
