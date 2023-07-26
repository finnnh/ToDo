package com.finn.todo.api

import com.finn.todo.api.dtos.NoteCreation
import com.finn.todo.api.dtos.NoteCreation.Companion.toNote
import com.finn.todo.api.dtos.NoteResponse
import com.finn.todo.api.dtos.NoteResponse.Companion.toResponseDTO
import com.finn.todo.domain.ToDoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class ToDoController(val toDoService: ToDoService) {

    @GetMapping("")
    fun getAllToDO(): List<NoteResponse> {
        return toDoService.getAllToDo().map { note -> note.toResponseDTO() };
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToDo(@RequestBody note: NoteCreation): NoteResponse {
        if(toDoService.getToDoByID(note.id) != null) throw ResponseStatusException(HttpStatus.CONFLICT, "There is already a note with id: $note.id ")
        return toDoService.addToDo(note.toNote()).toResponseDTO()
    }

    @GetMapping("{id}")
    fun getToDoByID(@PathVariable id: Int): NoteResponse {
        val note = toDoService.getToDoByID(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found");

        return note.toResponseDTO();
    }

    @PutMapping("{id}")
    fun checkToDo(@PathVariable id: Int): NoteResponse {
        val note = toDoService.getToDoByID(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found");

        return toDoService.checkToDo(note).toResponseDTO()
    }

    @DeleteMapping("{id}")
    fun removeToDo(@PathVariable id: Int) {
        val note = toDoService.getToDoByID(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found");

        toDoService.removeToDo(note)
    }
}