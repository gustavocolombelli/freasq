package com.freasq.exception

class SlugAlreadyExistsException(slug: String) : RuntimeException("O slug já está em uso.")