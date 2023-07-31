package com.finn.todo.domain.models

data class Note(var id: Int, val content: String, val description: String, var checked: Boolean)
