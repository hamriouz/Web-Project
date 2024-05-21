package com.webProject.country

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/web/countries")
interface CountryController {
    @GetMapping
    fun getCountries(): ResponseEntity<Any>

    @GetMapping("/{name}")
    fun getCountry(@PathVariable name: String): ResponseEntity<Any>

    @GetMapping("/{name}/weather")
    fun getCountryCapitalWeather(@PathVariable name: String): ResponseEntity<Any>

}