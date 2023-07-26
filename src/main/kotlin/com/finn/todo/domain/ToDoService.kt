package com.finn.todo.domain

import com.finn.todo.domain.models.Note
import org.springframework.stereotype.Service

@Service
class ToDoService {

    val notes = mutableListOf<Note>();

    fun getAllToDo(): List<Note> {
        return notes;
    }

    fun addToDo(note: Note): Note {
        notes.add(note)
        return note
    }

    fun getToDoByID(id: Int): Note? {
        return notes.firstOrNull { it.id == id };
    }

    fun checkToDo(note: Note): Note {
        note.checked = !note.checked
        return note
    }

    fun removeToDo(note: Note) {
        notes.remove(note);
    }

}