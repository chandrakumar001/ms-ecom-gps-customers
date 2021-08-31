package com.chandrakumar.ms.api.customer.mapper


import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CustomerViewQueryMapperSpec extends Specification {

    def "Failed::CustomerViewQueryMapper,Object creation"() {

        given: "a CustomerViewQueryMapper declaration"
        String errorMessage = null
        when: ""
        try {
            new CustomerViewQueryMapper();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'CustomerViewQueryMapper class'
    }
}
