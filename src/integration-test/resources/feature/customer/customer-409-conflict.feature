Feature: Test Customer API

  Background:
    * url baseUrl

  Scenario: Already the customer existing with email ID

    Given def req =
    """
        {
          "emailId": "osaimar12@gmail.com",
          "customerName": {
          "firstName": "sarah",
          "lastName": "r"
          },
          "age": "32",
          "favouriteColour": "blue"
        }
    """

    Given path 'ecoms/gps/v1/customers'
    And request req
    When method post
    Then status 409