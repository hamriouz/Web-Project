package com.webProject.country

import com.webProject.country.model.response.*
import org.springframework.cache.annotation.Cacheable
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class CountryService(
    @Value("\${web.country.get-countries.url}")
    private val getCountriesUrl: String,
    @Value("\${web.country.get-country.url}")
    private val getCountryUrl: String,
    @Value("\${web.country.get-country.header-title}")
    private val headerTitle: String,
    @Value("\${web.country.get-country.api-key}")
    private val apiKey: String,
    @Value("\${web.country.get-capital-weather.url}")
    private val getCapitalWeatherUrl: String,

    ) {
    @Cacheable(
        "getCountries",
        cacheManager = "redisCacheManager",
        key = "#root.methodName"
    )
    // todo add pagination
    fun getCountries(): CountyNameResponse {
        val itemList =
            RestTemplate().getForEntity(getCountriesUrl, CountriesSnowSpaceResponse::class.java).body!!.data!!
        val getCountriesResponse = CountyNameResponse().apply {
            this.count = itemList.size
            this.countries = getCountryName(itemList)
        }
        return getCountriesResponse
    }

    @Cacheable(
        "getCountry",
        cacheManager = "redisCacheManager",
        key = "#name"
    )
    fun getCountry(name: String): CountryResponse {
        val url = "$getCountryUrl?name=$name&$headerTitle=$apiKey"
        val countryHashList = RestTemplate().getForEntity(url, List::class.java).body!!.first()!!
        return createCountryDetail(countryHashList, name)
    }

    @Cacheable(
        "getCountryCapitalWeather",
        cacheManager = "redisCacheManager",
        key = "#cityName"
    )
    fun getCapitalWeather(cityName: String, countryName: String): WeatherResponse {
        val url = "$getCapitalWeatherUrl?city=$cityName&$headerTitle=$apiKey"
        val countryWeatherHashList = RestTemplate().getForEntity(url, Any::class.java).body!!
        return getCityTemperature(countryWeatherHashList, countryName, cityName)
    }

    private fun getCountryName(itemList: List<CountryData>): MutableList<CountryName> {
        val result = mutableListOf<CountryName>()
        itemList.forEach { item ->
            result.add(CountryName().apply { this.name = item.country })
        }
        return result
    }

    private fun createCountryDetail(itemList: Any, name: String): CountryResponse {
        val linkedHashMap = itemList as LinkedHashMap<*, *>
        val linkedHashMapCurrency = linkedHashMap.get("currency") as LinkedHashMap<*, *>
        return CountryResponse().apply {
            this.name = name
            this.capital = linkedHashMap.get("capital").toString()
            this.iso2 = linkedHashMap.get("iso2").toString()
            this.popGrowth = linkedHashMap.get("pop_growth").toString().toDouble()
            this.population = linkedHashMap.get("population").toString().toDouble()
            this.currency = CurrencyDto().apply {
                this.name = linkedHashMapCurrency.get("name").toString()
                this.code = linkedHashMapCurrency.get("code").toString()
            }
        }
    }

    private fun getCityTemperature(itemList: Any, country: String, city: String): WeatherResponse {
        val linkedHashMap = itemList as LinkedHashMap<*, *>
        return WeatherResponse().apply {
            this.countryName = country
            this.capital = city
            this.windSpeed = linkedHashMap.get("wind_speed").toString().toDouble()
            this.windDegrees = linkedHashMap.get("wind_degrees").toString().toDouble()
            this.temp = linkedHashMap.get("temp").toString().toInt()
            this.humidity = linkedHashMap.get("humidity").toString().toInt()
        }
    }
}




