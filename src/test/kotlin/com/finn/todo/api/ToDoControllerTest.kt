package com.finn.todo.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.finn.todo.api.dtos.NoteResponse
import com.finn.todo.domain.ToDoService
import com.finn.todo.domain.exceptions.AlreadyExistsException
import com.finn.todo.domain.models.Note
import io.mockk.every
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import com.ninjasquad.springmockk.MockkBean
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class ToDoControllerTest(@Autowired val  mockMvc: MockMvc) {

    @MockkBean
    lateinit var toDoService: ToDoService

    @Test
    fun `getToDoByID() Should return a Note with ID = 1`() {
        // given
        every { toDoService.getToDoByID(1) } returns Note(1, "", "", false)

        mockMvc.perform(MockMvcRequestBuilders.get("/1"))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("id").value(1))

    }

    @Test
    fun `getToDoByIDThatDoenstExist() Should throw a ResponseStatus Exception (NOT FOUND)`() {
        // given
        every { toDoService.getToDoByID(1) } returns null

        mockMvc.perform(MockMvcRequestBuilders.get("/1"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `getAllToDo() Should return a list of Something`() {
        every { toDoService.getAllToDo() } returns listOf(Note(1, "", "", false), Note(2, "", "", false))

        val result = mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().isOk)
            .andReturn()

        val mapper = jacksonObjectMapper()
        val notes: List<Note> = mapper.readValue(result.response.contentAsString)

        Assertions.assertThat(notes).isNotEmpty
    }

    @Test
    fun `addToDo() Should return the created Note & Ok Status`() {
        every { toDoService.addToDo(Note(1, "", "", false)) } returns Note(1, "", "", false)

        val note = Note(1, "", "", false)
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        val writer = mapper.writer().withDefaultPrettyPrinter()

        val requestJson = writer.writeValueAsString(note)

        mockMvc.perform(MockMvcRequestBuilders.post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isCreated)

    }

    @Test
    fun `addToDo() Should throw an AlreadyExistsException`() {
        every { toDoService.addToDo(any()) } throws AlreadyExistsException()

        val note = Note(1, "", "", false)
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        val writer = mapper.writer().withDefaultPrettyPrinter()

        val requestJson = writer.writeValueAsString(note)

        mockMvc.perform(MockMvcRequestBuilders.post("/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isConflict)
    }

    @Test
    fun `checkToDo() Should return a Note with changed Checked Value`() {
        val existingNote = Note(1, "", "", false)
        every { toDoService.getToDoByID(1) } returns existingNote
        every { toDoService.checkToDo(1) } returns existingNote.copy(checked = true)

        val result = mockMvc.perform(MockMvcRequestBuilders.put("/1"))
            .andExpect(status().isOk)
            .andReturn()

        val mapper = jacksonObjectMapper()
        val note: NoteResponse = mapper.readValue(result.response.contentAsString)

        Assertions.assertThat(note.checked).isEqualTo(true)
    }

    @Test
    fun `removeToDo() Should remove the ToDo`() {

        every { toDoService.removeToDo(1) } just runs

        every { toDoService.getToDoByID(1) } returns Note(1, "", "", false)

        mockMvc.perform(MockMvcRequestBuilders.delete("/1"))

        verify {
            toDoService.removeToDo(1)
        }

    }

}