//package com.webProject.country.test.weather.bdd;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = TestConfiguration.class)
//public class WeatherStepDefinition {
//    @Autowired
//    private MockMvc mvc;
//
//    private ResultActions action;
//
//    @When("the client calls \\/countries\\/iran\\/weather")
//    public void the_client_calls_weather() throws Exception {
//        action = mvc.perform(get("/countries/iran/weather").header("Authorization", "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMDEwOTQ2MCwiZXhwIjoxNzIwMTEwOTAwfQ.3xDyCQM4bDyq7jS5pVsVahWSqcum9es-64-t80hKl_SGZHhiAT4uhQeBaGwr1KGV"));
//    }
//
//    @Then("the client receives status code of 200")
//    public void the_client_receives_status_code_of_for_category() throws Exception {
//        action.andExpect(status().isOk());
//    }
//
//    @And("the client receives weather information")
//    public void the_client_receives_weather_information() throws Exception {
//        action.andExpect(jsonPath("capital").hasJsonPath())
//                .andExpect(jsonPath("wind_speed").hasJsonPath())
//                .andExpect(jsonPath("wind_degrees").hasJsonPath());
//    }
//}
