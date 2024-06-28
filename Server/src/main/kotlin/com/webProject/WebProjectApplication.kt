package com.webProject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class WebProjectApplication

fun main(args: Array<String>) {
	runApplication<WebProjectApplication>(*args)
}
