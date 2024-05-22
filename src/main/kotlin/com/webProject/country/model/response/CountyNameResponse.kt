package com.webProject.country.model.response

import java.io.Serializable

class CountyNameResponse : Serializable {
    var countries : MutableList<CountryName>? = null
    var count: Int = 0
}