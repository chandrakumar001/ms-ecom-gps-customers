Feature: Test customer API

  Background:
    * url baseUrl

  Scenario Outline: Retrieve the customer id,<testStep>,<customerId> and <expectedType> and <expectedErrorMessage>

    #Get the customer id
    Given path 'ecoms/gps/v1/customers/'+<customerId>
    When method get
    Then status <expectedCode>
    And match response.message == <expectedErrorMessage>
    And match response.type == <expectedType>

    Examples:
      | testStep                          | customerId                               | expectedCode | expectedType  | expectedErrorMessage                    |
      | "The customer id is null"           | null                                   | 400          | "Bad Request" | "The 'customerId' is invalid UUID format" |
      | "The customer id is invalid format" | 'd6f02a17-c676-4b1b'                   | 400          | "Bad Request" | "The 'customerId' is invalid UUID format" |
      | "The customer id is not found"      | 'd6f02a17-c676-4b1b-ae39-e3b12f47c407' | 404          | "Not Found"   | "The 'customerId' is not found"           |

  Scenario Outline: delete the customer id,<testStep>,<customerId> and <expectedType> and <expectedErrorMessage>

    #delete the customer id
    Given path 'ecoms/gps/v1/customers/'+<customerId>
    When method delete
    Then status <expectedCode>
    And match response.message == <expectedErrorMessage>
    And match response.type == <expectedType>

    Examples:
      | testStep                          | customerId                               | expectedCode | expectedType  | expectedErrorMessage                    |
      | "The customer id is null"           | null                                   | 400          | "Bad Request" | "The 'customerId' is invalid UUID format" |
      | "The customer id is invalid format" | 'd6f02a17-c676-4b1b'                   | 400          | "Bad Request" | "The 'customerId' is invalid UUID format" |
      | "The customer id is not found"      | 'd6f02a17-c676-4b1b-ae39-e3b12f47c407' | 404          | "Not Found"   | "The 'customerId' is not found"           |

  Scenario Outline: GET the customer id,<testStep>,<customerId> and <expectedType> and <expectedErrorMessage>

    #delete the customer id
    Given path 'ecoms/gps/v1/customers/'+<customerId>
    When method GET
    Then status <expectedCode>
    And match response.message == <expectedErrorMessage>
    And match response.type == <expectedType>

    Examples:
      | testStep                          | customerId                               | expectedCode | expectedType  | expectedErrorMessage                    |
      | "The customer id is null"           | null                                   | 400          | "Bad Request" | "The 'customerId' is invalid UUID format" |
      | "The customer id is invalid format" | 'd6f02a17-c676-4b1b'                   | 400          | "Bad Request" | "The 'customerId' is invalid UUID format" |
      | "The customer id is not found"      | 'd6f02a17-c676-4b1b-ae39-e3b12f47c407' | 404          | "Not Found"   | "The 'customerId' is not found"           |