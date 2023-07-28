package com.finn.todo.api

import com.finn.todo.api.dtos.NoteCreation
import com.finn.todo.api.dtos.NoteCreation.Companion.toNote
import com.finn.todo.api.dtos.NoteResponse
import com.finn.todo.api.dtos.NoteResponse.Companion.toResponseDTO
import com.finn.todo.domain.ToDoService
import com.finn.todo.domain.exceptions.AlreadyExistsException
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

    init {
        println("ToDoController started")
    }

    @GetMapping("")
    fun getAllToDO(): List<NoteResponse> {
        return toDoService.getAllToDo().map { note -> note.toResponseDTO() };
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    fun addToDo(@RequestBody note: NoteCreation): NoteResponse {
        try {
            return toDoService.addToDo(note.toNote()).toResponseDTO()
        } catch (exception: AlreadyExistsException) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "There is already a note with id: $note.id ")
        }
    }

    @GetMapping("{id}")
    fun getToDoByID(@PathVariable id: Int): NoteResponse {
        return toDoService.getToDoByID(id)?.toResponseDTO() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found")
    }

    @PutMapping("{id}")
    fun checkToDo(@PathVariable id: Int): NoteResponse {
        val note = toDoService.getToDoByID(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found");

        return toDoService.checkToDo(id).toResponseDTO()
    }

    @DeleteMapping("{id}")
    fun removeToDo(@PathVariable id: Int) {
        val note = toDoService.getToDoByID(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "ToDo not found");

        toDoService.removeToDo(id)
    }

}