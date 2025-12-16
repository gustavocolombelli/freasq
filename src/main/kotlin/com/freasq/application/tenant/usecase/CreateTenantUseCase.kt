package com.freasq.application.tenant.usecase

import com.freasq.domain.tenant.model.Tenant
import com.freasq.domain.tenant.model.TenantStatus
import com.freasq.domain.tenant.repository.TenantRepository
import org.springframework.stereotype.Component

@Component
class CreateTenantUseCase(
    private val tenantRepository: TenantRepository
){
    fun execute(name: String, slug: String): Tenant {
        if (tenantRepository.existsBySlug(slug)) {
            throw IllegalArgumentException("Slug já existente")
        }

        val tenant = Tenant(
            name = name,
            slug = slug,
            status = TenantStatus.ACTIVE
        )

        return tenantRepository.save(tenant)
    }
}
