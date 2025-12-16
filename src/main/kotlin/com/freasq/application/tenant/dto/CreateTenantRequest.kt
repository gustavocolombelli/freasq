package com.freasq.application.tenant.dto

data class CreateTenantRequest(
    val name: String,
    val slug: String
)