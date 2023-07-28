package com.finn.todo

import com.finn.todo.domain.ToDoService
import com.finn.todo.domain.models.Note
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    init {
        println("Config started")
    }

    @Bean
    fun getInitialNotes(): List<Note> {
        return listOf(Note(1, "", "", false))
    }

    /*
        @Bean
        fun toDoServiceBean(): ToDoService {
            println("Creating ToDo Service Bean")
            return ToDoService()
        }
    */



}