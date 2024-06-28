package com.webProject.country.model.response

import java.io.Serializable

class CountryResponse : Serializable {
    var name: String? = null
    var capital: String? = null
    var iso2: String? = null
    var population: Double? = null
    var popGrowth: Double? = null
    var currency: CurrencyDto? = null
}