package com.freasq.exception

import java.util.UUID

class TenantNotFoundException(publicId: UUID) :
    RuntimeException("Tenant com o ID '$publicId' não foi encontrado.")
