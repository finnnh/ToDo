package com.finn.todo

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ToDoController(val toDoService: ToDoService) {

    @GetMapping("")
    fun getAllToDO(): List<Note> {
        return toDoService.getAllToDo();
    }

    @PostMapping("")
    fun addToDo(@RequestBody note: Note) {
        toDoService.addToDo(note)
    }

    @GetMapping("{id}")
    fun getToDoByID(@PathVariable id: Int): Note {
        return toDoService.getToDoByID(id)?: Note(-1, "", "", false)
    }

    @PutMapping("{id}")
    fun checkToDo(@PathVariable id: Int) {
        toDoService.checkToDo(id)
    }

    @DeleteMapping("{id}")
    fun deleteToDo(@PathVariable id: Int) {
        toDoService.removeToDo(id)
    }
}