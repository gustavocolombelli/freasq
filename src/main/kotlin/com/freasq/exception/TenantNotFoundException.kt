package com.freasq.exception

import java.util.UUID

class TenantNotFoundException(publicId: UUID) :
    ResourceNotFoundException("Tenant com o ID '$publicId' não foi encontrado.")
