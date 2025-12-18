package com.freasq.controller.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateTenantRequest(
    @field:NotBlank(message = "O nome não pode ser vazio") @field:Size(max = 150, min = 5) val name: String,
    @field:NotBlank(message = "O slug não pode ser vazio") @field:Size(max = 100, min = 6) val slug: String
)