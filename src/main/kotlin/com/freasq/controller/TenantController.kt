package com.freasq.controller

import com.freasq.controller.dto.CreateTenantRequest
import com.freasq.controller.dto.TenantResponse
import com.freasq.controller.response.ApiResponse
import com.freasq.service.TenantService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/tenants")
class TenantController(
    private val tenantService: TenantService
) {

    @PostMapping
    fun create(
        @Valid @RequestBody request: CreateTenantRequest
    ): ResponseEntity<ApiResponse<TenantResponse>> {
        val tenant = tenantService.create(
            request.name,
            request.slug
        )
        val response = TenantResponse.from(tenant)

        val location: URI = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{publicId}")
            .buildAndExpand(tenant.publicId)
            .toUri()

        return ResponseEntity.created(location).body(ApiResponse.success(response))
    }

    @GetMapping("/{publicId}")
    fun get(@PathVariable publicId: UUID): ResponseEntity<ApiResponse<TenantResponse>> {
        val tenant = tenantService.getByPublicId(publicId)
        val response = TenantResponse.from(tenant)
        return ResponseEntity.ok(ApiResponse.success(response))
    }
}
