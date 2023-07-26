package com.finn.todo.api.dtos

import com.finn.todo.domain.models.Note

data class NoteResponse(var id: Int, val content: String, val description: String, var checked: Boolean) {

    companion object {
        fun Note.toDTO(): NoteResponse {
            return NoteResponse(this.id, this.content, this.description, this.checked)
        }
    }

}