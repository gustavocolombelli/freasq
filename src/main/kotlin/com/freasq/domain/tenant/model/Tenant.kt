package com.freasq.domain.tenant.model

import java.time.LocalDateTime
import java.util.UUID

class Tenant(
    val id: Long = 0,
    val publicId: UUID = UUID.randomUUID(),
    var name: String,
    var slug: String,
    var status: TenantStatus,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()

)
