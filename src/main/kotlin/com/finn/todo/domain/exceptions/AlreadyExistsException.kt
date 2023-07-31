package com.finn.todo.domain.exceptions

class AlreadyExistsException(message: String = "Resource already exists") : RuntimeException(message)
