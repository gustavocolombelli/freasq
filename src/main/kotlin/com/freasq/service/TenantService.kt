package com.freasq.service

import com.freasq.entity.Tenant
import com.freasq.exception.SlugAlreadyExistsException
import com.freasq.exception.TenantNotFoundException
import com.freasq.repository.TenantRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TenantService(
    private val tenantRepository: TenantRepository
) {

    fun create(name: String, slug: String): Tenant {
        if (tenantRepository.existsBySlug(slug)) {
            throw SlugAlreadyExistsException(slug)
        }

        val tenant = Tenant(
            name = name,
            slug = slug
        )

        return tenantRepository.save(tenant)
    }

    fun getByPublicId(publicId: UUID): Tenant =
        tenantRepository.findByPublicId(publicId)
            ?: throw TenantNotFoundException(publicId)
}
