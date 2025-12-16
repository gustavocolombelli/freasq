package com.freasq.web

import com.freasq.application.tenant.dto.CreateTenantRequest
import com.freasq.application.tenant.dto.TenantResponse
import com.freasq.application.tenant.usecase.CreateTenantUseCase
import com.freasq.application.tenant.usecase.GetTenantByPublicIdUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/tenants")
class TenantController(
    private val createTenantUseCase: CreateTenantUseCase,
    private val getTenantPublicIdUseCase: CreateTenantUseCase,
    private val getTenantByPublicIdUseCase: GetTenantByPublicIdUseCase
){
    @PostMapping
    fun create(
        @RequestBody @Valid request: CreateTenantRequest
    ): ResponseEntity<TenantResponse>{
        val tenant = createTenantUseCase.execute(
            name = request.name,
            slug = request.slug
        )
    return ResponseEntity.ok(TenantResponse.from(tenant))

    }
    @GetMapping("/{publicId}")
    fun getByPublicId(
        @PathVariable publicId: UUID
    ): ResponseEntity<TenantResponse> {
        val tenant = getTenantByPublicIdUseCase.execute(publicId)
        return ResponseEntity.ok(TenantResponse.from(tenant))
    }
}