package com.webProject.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.webProject.WebProjectApplicationTests
import com.webProject.user.UserRepository
import com.webProject.user.model.UserType
import com.webProject.user.model.request.UserDetail
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class UserServiceTest(
    private val objectMapper: ObjectMapper,
    private val mockMvc: MockMvc,
    private val userRepository: UserRepository,
) : WebProjectApplicationTests() {
    companion object {
        @AfterAll
        @JvmStatic
        fun cleanUp(
            @Autowired userRepository: UserRepository,
        ) {
            userRepository.deleteAll()
        }
    }
    @Test
    fun registerUserTest() {
        val request = UserDetail("test_hamrazzzzz", "p@ssword")
        mockMvc.perform(post("/api/web/users/register")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
        )
        val createdUser = userRepository.findByName(request.name)!!
        assertEquals(createdUser.name, request.name)
        assertNotEquals(createdUser.password, request.password)
        assertFalse(createdUser.active!!)
        assertEquals(createdUser.type, UserType.USER)
        assertNotNull(createdUser.id)

        userRepository.delete(createdUser)

    }
}