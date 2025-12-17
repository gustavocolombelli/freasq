package com.freasq.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "tenant")
class Tenant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "public_id", nullable = false, unique = true, updatable = false)
    val publicId: UUID = UUID.randomUUID(),

    @Column(name = "name", length = 150, nullable = false)
    var name: String,

    @Column(name = "slug", length = 100, nullable = false, unique = true)
    var slug: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30, nullable = false)
    var status: TenantStatus = TenantStatus.ACTIVE,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime? = null
)