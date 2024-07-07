Feature: Verify the weather service
  Scenario: Client makes call to GET weather details of a country
    When the client calls /countries/iran/weather
    Then the client receives status code of 200
    And the client receives weather information