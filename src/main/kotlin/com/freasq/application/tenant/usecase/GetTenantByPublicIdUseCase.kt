package com.freasq.application.tenant.usecase

import com.freasq.domain.tenant.model.Tenant
import com.freasq.domain.tenant.repository.TenantRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class GetTenantByPublicIdUseCase(
    private val tenantRepository: TenantRepository
) {

    fun execute(publicId: UUID): Tenant =
        tenantRepository.findByPublicId(publicId)
            ?: throw IllegalArgumentException("Tenant não encontrado")
}
