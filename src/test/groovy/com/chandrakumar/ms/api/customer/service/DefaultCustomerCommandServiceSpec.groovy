package com.chandrakumar.ms.api.customer.service

import com.chandrakumar.ms.api.common.audit.Action
import com.chandrakumar.ms.api.customer.entity.Customer
import com.chandrakumar.ms.api.customer.repository.CustomerRepository
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO
import org.springframework.data.domain.AuditorAware
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.customer.service.CustomerMockData.customer
import static com.chandrakumar.ms.api.customer.service.CustomerMockData.customerBareDTO
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_CUSTOMER_ID_IS_NOT_FOUND
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_THE_EMAIL_IS_ALREADY_EXISTS

@Unroll
class DefaultCustomerCommandServiceSpec extends Specification {

    public static final String USER_NAME = "admin"
    private CustomerCommandService commandService
    def customerRepository = Mock(CustomerRepository)
    def auditorAware = Mock(AuditorAware)

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        commandService = new DefaultCustomerCommandService(
                customerRepository,
                auditorAware
        )
    }

    def "Success::customer IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns"

        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final CustomerBareDTO customerBareDTO = customerBareDTO(emailId, firstName, lastName, age)
        final Customer customer = customer(emailId, firstName, lastName, age)
        customer.action = Action.CREATED

        customerRepository.findByEmailId(_ as String) >> Optional.empty()
        customerRepository.save(_ as Customer) >> customer

        when: "that customer is saved in the DB"
        CustomerDTO customerDTO = commandService.createCustomer(customerBareDTO)

        then: "the ID is correctly logged"
        customerDTO?.data?.emailId == emailId
        customerDTO?.data?.age == age
        and:
        1 * customerRepository.save(_ as Customer) >> customer
    }

    def "Failed::customer IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns"

        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"
        def actualErrorMessage = null

        final CustomerBareDTO customerBareDTO = customerBareDTO(emailId, firstName, lastName, age)
        customerRepository.findByEmailId(_ as String) >> Optional.of(mockDBCustomerData)

        when: "that customer is saved in the DB"
        try {
            commandService.createCustomer(customerBareDTO)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                              | emailId               | mockDBCustomerData                                        || expectedErrorMessage
        "Email id already exists in database" | "osaimar19@gmail.com" | customer("osaimar19@gmail.com", "chandra", "kumar", "21") || ERROR_THE_EMAIL_IS_ALREADY_EXISTS

    }

    def "Success::updateCustomer IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns an ID to customer"
        def customerId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final CustomerBareDTO customerBareDTO = customerBareDTO(emailId, firstName, lastName, age)
        final Customer customer = customer(emailId, firstName, lastName, age)
        customer.action = Action.UPDATED

        customerRepository.findByCustomerId(_ as UUID) >> Optional.of(customer)
        customerRepository.save(_ as Customer) >> customer

        when: "that customer is saved in the DB"
        CustomerDTO customerDTO = commandService.updateCustomer(customerId, customerBareDTO)

        then: "the ID is correctly logged"
        customerDTO?.data?.emailId == emailId
        customerDTO?.data?.age == age
    }

    def "Failed::updateCustomer IDs are logged whenever they are saved in the DB"() {
        given: "a Customer that assigns an ID to Customer"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"
        def actualErrorMessage = null

        final CustomerBareDTO customerBareDTO = customerBareDTO(emailId, firstName, lastName, age)
        customerRepository.findByCustomerId(_ as UUID) >> mockDBCustomerData

        when: "that customer is saved in the DB"
        try {
            commandService.updateCustomer(customerId, customerBareDTO)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                        | customerId                             | mockDBCustomerData || expectedErrorMessage
        "The customerId invalid format" | "d6f02a17-c676-4b1b-ae39"              | Optional.empty()   || ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT
        "The customer is not found"     | "d6f02a17-c676-4b1b-ae39-e3b12f47c407" | Optional.empty()   || ERROR_CUSTOMER_ID_IS_NOT_FOUND
    }

    def "Success::deleteCustomer IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns an ID to customer"
        def customerId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final Customer customer = customer(emailId, firstName, lastName, age)
        customer.action = Action.UPDATED

        customerRepository.findByCustomerId(_ as UUID) >> Optional.of(customer)
        customerRepository.softDeleteByCustomerId(_ as UUID, null, null) >> null
        auditorAware.getCurrentAuditor() >> Optional.of(USER_NAME)

        when: "that customer is saved in the DB"
        commandService.deleteCustomer(customerId)

        then: "the ID is correctly logged"
        1 * customerRepository.softDeleteByCustomerId(_ as UUID, _ as Date, _ as String) >> null
    }

    def "Failed::deleteCustomer IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns an ID to customer"

        def actualErrorMessage = null
        customerRepository.findByCustomerId(_ as UUID) >> mockDBCustomerData

        when: "that customer is saved in the DB"
        try {
            commandService.deleteCustomer(customerId)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep                        | customerId                             | mockDBCustomerData || expectedErrorMessage
        "The customerId invalid format" | "d6f02a17-c676-4b1b-ae39"              | Optional.empty()   || ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT
        "The customer is not found"     | "d6f02a17-c676-4b1b-ae39-e3b12f47c407" | Optional.empty()   || ERROR_CUSTOMER_ID_IS_NOT_FOUND
    }
}
