package com.finn.todo.domain

import com.finn.todo.domain.exceptions.AlreadyExistsException
import com.finn.todo.domain.exceptions.NotExistException
import com.finn.todo.domain.models.Note
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ToDoServiceTest {

    @Test
    fun `addTodo() Should create a new Note`() {
        // given
        val todoService = ToDoService()
        val note = Note(1, "", "", false)
        // when
        val result = todoService.addToDo(note)
        // then
        Assertions.assertThat(result).isEqualTo(note)
    }

    @Test
    fun `addMultipleTodoWithSameID() Should throw a NotFoundException`() {
        // given
        val toDoService = ToDoService()
        val note1 = Note(1, "", "", false)
        val note2 = Note(1, "c1", "_", false)
        toDoService.addToDo(note1)

        // then
        assertThrows<AlreadyExistsException> {
            // when
            toDoService.addToDo(note2)
        }
    }

    @Test
    fun `getToDoByID() Should return a Note with the given ID`() {
        // given
        val toDoService = ToDoService()
        toDoService.addToDo(Note(1, "Clean", "Desc", false))

        // when
        val result = toDoService.getToDoByID(1)

        // then
        Assertions.assertThat(result!!.id).isEqualTo(1)
    }

    @Test
    fun `getToDoByIDThatDoesntExist() Should return null`() {
        // given
        val toDoService = ToDoService()

        // when
        val result = toDoService.getToDoByID(1)

        //then
        Assertions.assertThat(result).isNull()
    }

    @Test
    fun `getAllTodo() Should return a list of ToDo's`() {
        // given
        val toDoService = ToDoService()
        toDoService.addToDo(Note(1, "C", "Decs", false))

        // when
        val list = toDoService.getAllToDo()

        // then
        Assertions.assertThat(list).isNotEmpty()
    }

    @Test
    fun `checkToDo() Should check the Note`() {
        // given
        val toDoService = ToDoService()
        toDoService.addToDo(Note(1, "C", "Desc", false))
        val note = toDoService.getToDoByID(1)

        // when
        val result = toDoService.checkToDo(1)

        // the
        Assertions.assertThat(result.checked).isEqualTo(true)
    }

    @Test
    fun `unCheckToDo() Should uncheck the Note`() {
        // given
        val toDoService = ToDoService()
        toDoService.addToDo(Note(1, "C", "Decs", false))
        val note = toDoService.getToDoByID(1)
        note?.let { toDoService.checkToDo(note.id) };

        // when
        val result = note?.let { toDoService.checkToDo(note.id) };

        // then
        Assertions.assertThat(result!!.checked).isEqualTo(false)
    }

    @Test
    fun `checkNonExistToDo() Should throw NotExistException`() {
        // given
        val toDoService = ToDoService()

        // then
        assertThrows<NotExistException> {
            // when
            toDoService.checkToDo(1)
        }
    }

    @Test
    fun `removeToDo() Should return an Empty List`() {
        // given
        val toDoService = ToDoService()
        toDoService.addToDo(Note(1, "C", "Desc", false))

        // when
        toDoService.removeToDo(1)

        // then
        Assertions.assertThat(toDoService.getAllToDo()).isEmpty()
    }

    @Test
    fun `removeMultipleToDos() Should return an Empty List`() {
        // given
        val toDoService = ToDoService()
        toDoService.addToDo(Note(1, "C", "Desc", false))
        toDoService.addToDo(Note(2, "C", "Desc", false))

        // when
        toDoService.removeToDo(1)
        toDoService.removeToDo(2)

        Assertions.assertThat(toDoService.getAllToDo()).isEmpty()
    }

    @Test
    fun `removeToDoThatDoenstExist() Should throw a NotFoundExeption`() {
        // given
        val toDoService = ToDoService()

        // then
        assertThrows<NotExistException> {
           // when
            toDoService.removeToDo(1)
        }
    }

}