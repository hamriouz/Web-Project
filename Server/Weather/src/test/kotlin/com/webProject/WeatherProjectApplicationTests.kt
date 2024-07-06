package com.webProject

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

@AutoConfigureMockMvc
@DirtiesContext
@ActiveProfiles("test")
@SpringBootTest(
	classes = [WeatherProjectApplication::class],
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)class WeatherProjectApplicationTests {
	@Test
	fun contextLoads() {
	}

}
