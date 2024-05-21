package com.webProject.country

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class CountryControllerImp(
    private val countryService: CountryService,

    ) : CountryController {
    override fun getCountry(name: String): ResponseEntity<Any> {
        val country = countryService.getCountry(name)
        return ResponseEntity.ok(country)
    }

    // todo add pagination
    override fun getCountries(): ResponseEntity<Any> {
        val countries = countryService.getCountries()
        return ResponseEntity.ok(countries)
    }

        override fun getCountryCapitalWeather(name: String): ResponseEntity<Any> {
            val capital = countryService.getCountry(name).capital
            val countryWeather = countryService.getCapitalWeather(capital!!, name)
            return ResponseEntity.ok(countryWeather)
    }
}