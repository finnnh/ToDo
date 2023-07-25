package com.finn.todo

import org.springframework.stereotype.Service
import kotlin.random.Random

data class Note(var id: Int, val content: String, val description: String, var checked: Boolean)

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

    fun getToDoByID(id: Int): Note {
        return notes.filter {it.id == id }.first()
    }

    fun checkToDo(id: Int) {
        val note = getToDoByID(id)
        note.checked = !note.checked
    }

    fun removeToDo(id: Int) {
        notes.remove(getToDoByID(id));
    }

}