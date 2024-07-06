package com.webProject.country.model.response

import java.io.Serializable

class WeatherResponse : Serializable {
    var countryName: String? = null
    var capital: String? = null
    var windSpeed: Double? = null
    var windDegrees: Double? = null
    var temp: Int? = null
    var humidity: Int? = null

}