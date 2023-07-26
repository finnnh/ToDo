package com.finn.todo.domain

import com.finn.todo.domain.models.Note
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class ToDoService {

    val notes = mutableListOf<Note>();

    fun getAllToDo(): List<Note> {
        return notes;
    }

    fun addToDo(note: Note) {
        note.id = Random.nextInt()

        notes.add(note)
    }

    fun getToDoByID(id: Int): Note? {
        return notes.filter {it.id == id }.firstOrNull();
    }

    fun checkToDo(id: Int) {
        val note = getToDoByID(id)

        if (note != null) {
            note.checked = !note.checked
        }
    }

    fun removeToDo(id: Int) {
        notes.remove(getToDoByID(id));
    }

}