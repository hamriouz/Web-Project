package com.webProject.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.webProject.WebProjectApplicationTests
import com.webProject.user.UserRepository
import com.webProject.user.model.request.UserDetail
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class AdminServiceTest(
    private val objectMapper: ObjectMapper,
    private val mockMvc: MockMvc,
    private val userRepository: UserRepository,
): WebProjectApplicationTests() {
    @AfterEach
    fun cleanUp() {
        val admin = userRepository.findByName("admin")
        val users = userRepository.findAll()
        users.remove(admin)
        userRepository.deleteAll(users)
    }

    @Test
    fun changeUserActivationTest() {
        val name = "onee"
        val password = "pass"
        createUser(name, password)
        var createdUser = userRepository.findByName(name)!!
        assertFalse(createdUser.active!!)
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/web/admin/users/${name}/true")
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        createdUser = userRepository.findByName(name)!!
        assertTrue(createdUser.active!!)
        userRepository.delete(createdUser)
    }

    @Test
    fun getUsersTest() {
        createUser("one", "pass")
        createUser("twoo", "pass2")
        val result = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/web/admin/users")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk).andReturn()

        val users = objectMapper.readValue(result.response.contentAsString, List::class.java)
        assertEquals(2, users.size)
    }

    private fun createUser(name: String, password: String) {
        val request = UserDetail(name, password)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/web/users/register")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )

    }
}