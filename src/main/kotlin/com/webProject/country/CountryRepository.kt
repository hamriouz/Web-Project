package com.webProject.country

import com.webProject.country.model.Country
import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository



@Repository
interface CountryRepository : CrudRepository<Country, Int> {

}