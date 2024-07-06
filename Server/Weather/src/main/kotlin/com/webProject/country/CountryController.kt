package com.webProject.country

import com.webProject.country.model.response.CountryResponse
import com.webProject.country.model.response.WeatherResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"], allowCredentials = "true")
@RequestMapping("/api/web/countries")
class CountryController(
    private val countryService: CountryService,
    ) {
    @GetMapping
    fun getCountries(): ResponseEntity<String> {
        val countries = countryService.getCountries()
        return ResponseEntity.ok(countries)
    }

    @GetMapping("/{name}")
    fun getCountry(@PathVariable name: String): ResponseEntity<CountryResponse> {
        val country = countryService.getCountry(name)
        return ResponseEntity.ok(country)
    }
    @GetMapping("/{name}/weather")
    fun getCountryCapitalWeather(@PathVariable name: String): ResponseEntity<WeatherResponse> {
            val capital = countryService.getCountry(name).capital
            val countryWeather = countryService.getCapitalWeather(capital!!, name)
            return ResponseEntity.ok(countryWeather)
    }
}