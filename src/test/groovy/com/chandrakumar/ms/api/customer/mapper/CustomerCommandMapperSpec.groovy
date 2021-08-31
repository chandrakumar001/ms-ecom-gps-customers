package com.chandrakumar.ms.api.customer.mapper


import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CustomerCommandMapperSpec extends Specification {

    def "Failed::CustomerCommandMapper,Object creation"() {

        given: "a CustomerCommandMapper declaration"
        String errorMessage = null
        when: ""
        try {
            new CustomerCommandMapper();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'CustomerCommandMapper class'
    }
}
