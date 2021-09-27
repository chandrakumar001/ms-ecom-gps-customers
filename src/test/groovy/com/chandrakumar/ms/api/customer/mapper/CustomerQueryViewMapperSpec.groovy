package com.chandrakumar.ms.api.customer.mapper


import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CustomerQueryViewMapperSpec extends Specification {

    def "Failed::CustomerQueryViewMapper,Object creation"() {

        given: "a CustomerQueryViewMapper declaration"
        String errorMessage = null
        when: ""
        try {
            new CustomerQueryViewMapper();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'CustomerQueryViewMapper class'
    }
}
