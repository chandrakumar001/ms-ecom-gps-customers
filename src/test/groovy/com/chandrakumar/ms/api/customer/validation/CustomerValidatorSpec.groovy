package com.chandrakumar.ms.api.customer.validation


import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.customer.service.CustomerMockData.customerBareDTO
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_EMAIL_ID_IS_INVALID_FORMAT
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_THE_AGE_MUST_BE_18
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.NUMBER_FORMAT_EXCEPTION_OCCURRED
import static com.chandrakumar.ms.api.customer.validation.CustomerValidator.validateCustomerDTO

@Unroll
class CustomerValidatorSpec extends Specification {

    def "Failed::CustomerValidator,Object creation"() {

        given: "a CustomerValidator declaration"
        String errorMessage = null
        when: ""
        try {
            new CustomerValidator();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'CustomerValidator class'
    }

    def "Failed::validateCustomerDTO, CustomerDTO Object"() {
        given: "a actualErrorMessage declaration"
        Optional<String> actualErrorMessage = null

        when: "call that validateCustomerDTO() method"
        actualErrorMessage = validateCustomerDTO(mockDBCustomerData)

        then: "Verify the error message"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                          | mockDBCustomerData                                                 || expectedErrorMessage
        "The email is invalid format"     | customerBareDTO("osaimar", "chandra", "kumar", "21")               || Optional.of(ERROR_EMAIL_ID_IS_INVALID_FORMAT)
        "Invalid number format for 'Age'" | customerBareDTO("osaimar19@gmail.com", "chandra", "kumar", "test") || Optional.of(NUMBER_FORMAT_EXCEPTION_OCCURRED)
        "The 'Age' is below 18"           | customerBareDTO("osaimar19@gmail.com", "chandra", "kumar", "17")   || Optional.of(ERROR_THE_AGE_MUST_BE_18)
        "The 'Age' exactly being 18"      | customerBareDTO("osaimar19@gmail.com", "chandra", "kumar", "18")   || Optional.empty()
        "All attribute value are correct" | customerBareDTO("osaimar19@gmail.com", "chandra", "kumar", "21")   || Optional.empty()
    }
}
