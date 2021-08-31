package com.chandrakumar.ms.api.customer.validation


import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.customer.validation.PageRequestValidator.THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE
import static com.chandrakumar.ms.api.customer.validation.PageRequestValidator.validateRequest
import static com.chandrakumar.ms.api.customer.validation.PageRequestValidator.THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE

@Unroll
class PageRequestValidatorSpec extends Specification {

    def "Failed::PageRequestBuild,Object creation"() {

        given: "a PageRequestValidator declaration"
        String errorMessage = null
        when: ""
        try {
            new PageRequestValidator();
        } catch (Exception e) {
            errorMessage = e.getMessage()
        }

        then: "Verify the error message"
        errorMessage == 'PageRequestValidator class'
    }

    def "Failed::validateRequest,#testStep,#page,#size==#expectedErrorMessage"() {
        given: "a actualErrorMessage declaration"
        Optional<String> actualErrorMessage = null
        when: "call that validateCustomerDTO() method"
        actualErrorMessage = validateRequest(
                page,
                size
        )

        then: "Verify the error message"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                          | page | size || expectedErrorMessage
        "The page and size is null"       | null | null || Optional.empty()
        "The page is null and size is 0"  | null | 0    || Optional.of(THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE)
        "The page is null and size is -1" | null | -1   || Optional.of(THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE)
        "The page is null and size is 1"  | null | 1    || Optional.empty()
        "The size is null and page is 0"  | 0    | null || Optional.of(THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE)
        "The size is null and page is -1" | -1   | null || Optional.of(THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE)
        "The size is null and page is 1"  | 1    | null || Optional.empty()
        "The size is  1 and size is 0"    | null | null || Optional.empty()
    }
}