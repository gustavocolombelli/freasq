package com.freasq.infrastructure.persistence.tenant

import com.freasq.domain.tenant.model.Tenant
import com.freasq.domain.tenant.model.TenantStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tenant")
class TenantEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "public_id", nullable = false, unique = true)
    val publicId: UUID,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var slug: String,

    @Column(nullable = false)
    var status: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime
) {

    companion object {
        fun fromDomain(tenant: Tenant): TenantEntity =
            TenantEntity(
                id = tenant.id,
                publicId = tenant.publicId,
                name = tenant.name,
                slug = tenant.slug,
                status = tenant.status.name,
                createdAt = tenant.createdAt,
                updatedAt = tenant.updatedAt
            )
    }

    fun toDomain(): Tenant =
        Tenant(
            id = id,
            publicId = publicId,
            name = name,
            slug = slug,
            status = TenantStatus.valueOf(status),
            createdAt = createdAt,
            updatedAt = updatedAt
        )
}
