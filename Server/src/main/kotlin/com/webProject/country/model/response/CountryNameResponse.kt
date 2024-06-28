package com.webProject.country.model.response

import java.io.Serializable

class CountryNameResponse : Serializable {
    var countries : MutableList<CountryName>? = null
    var count: Int = 0
}