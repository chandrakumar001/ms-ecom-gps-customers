Feature: Test customer API

  Background:
    * url baseUrl

  #@ignore
  Scenario: Create a Customer, ADD & REMOVE a Customer

    Given def req =
    """
        {
          "emailId": "sarah.r5@gmail.com",
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
    Then status 201
    * def id = response.customerId

    #Get the customer id
    Given path 'ecoms/gps/v1/customers/'+id
    When method get
    Then status 200
    And assert response.data.customerName.firstName == "sarah"
    And assert response.data.favouriteColour == "blue"

    ## update the payload
    Given def updateReq =
    """
        {
          "emailId": "sarah.r5@gmail.com",
          "customerName": {
          "firstName": "sarahUpdated",
          "lastName": "r"
          },
          "age": "32",
          "favouriteColour": "red"
        }
    """
    Given path 'ecoms/gps/v1/customers/'+id
    And request updateReq
    When method put
    Then status 202
    * def id = response.customerId

    #Get the customer id
    Given path 'ecoms/gps/v1/customers/'+id
    When method get
    Then status 200
    And assert response.data.customerName.firstName == "sarahUpdated"
    And assert response.data.favouriteColour == "red"

    #Delete the customer id
    Given path 'ecoms/gps/v1/customers/'+id
    When method delete
    Then status 204

    #Again Get the customer id
    Given path 'ecoms/gps/v1/customers/'+id
    When method get
    Then status 404

    #Again Put the customer id
    Given path 'ecoms/gps/v1/customers/'+id
    And request updateReq
    When method put
    Then status 404

    #Again delete the customer id
    Given path 'ecoms/gps/v1/customers/'+id
    When method delete
    Then status 404

    # Get all customer
    Given path 'ecoms/gps/v1/customers'
    When method GET
    Then status 200
    And assert response.count == 5