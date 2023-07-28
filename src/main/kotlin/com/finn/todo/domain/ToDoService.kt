package com.finn.todo.domain

import com.finn.todo.domain.exceptions.AlreadyExistsException
import com.finn.todo.domain.exceptions.NotExistException
import com.finn.todo.domain.models.Note
import org.springframework.stereotype.Service

@Service
class ToDoService() {

    private final val notes = mutableListOf<Note>();

    fun getAllToDo(): List<Note> {
        return notes;
    }

    fun addToDo(note: Note): Note {
        if(getToDoByID(note.id) != null) throw AlreadyExistsException();

        notes.add(note)
        return note
    }

    fun getToDoByID(id: Int): Note? {
        return notes.firstOrNull { it.id == id }
    }

    fun checkToDo(id: Int): Note {
        val note = getToDoByID(id) ?: throw NotExistException()
        note.checked = !note.checked
        return note
    }

    fun removeToDo(id: Int) {
        val note = getToDoByID(id) ?: throw NotExistException()
        notes.remove(note);
    }

}