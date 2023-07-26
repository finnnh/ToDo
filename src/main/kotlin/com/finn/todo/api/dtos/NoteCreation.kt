package com.finn.todo.api.dtos

import com.finn.todo.domain.models.Note

data class NoteCreation(var id: Int, val content: String, val description: String, var checked: Boolean) {

    companion object {
        fun Note.toCreationDTO(): NoteCreation {
            return NoteCreation(this.id, this.content, this.description, this.checked)
        }

        fun NoteCreation.toNote(): Note{
            return Note(this.id, this.content, this.description, this.checked)
        }
    }

}