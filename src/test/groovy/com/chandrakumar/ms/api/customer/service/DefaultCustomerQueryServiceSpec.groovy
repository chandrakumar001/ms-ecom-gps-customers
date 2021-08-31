package com.chandrakumar.ms.api.customer.service

import com.chandrakumar.ms.api.common.audit.Action
import com.chandrakumar.ms.api.customer.entity.Customer
import com.chandrakumar.ms.api.customer.entity.CustomerViewQuery
import com.chandrakumar.ms.api.customer.repository.CustomerViewQueryRepository
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.customer.service.CustomerViewQueryMockData.customerViewQuery
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_CUSTOMER_ID_IS_NOT_FOUND
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_NO_RECORD_FOUND
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT

@Unroll
class DefaultCustomerQueryServiceSpec extends Specification {

    public static final int CUSTOMER_TOTAL_COUNT = 1
    public static final int FIRST_INDEX = 0
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final int DEFAULT_SIZE_NUMBER = 10;
    public static final int TOTAL_RECORD_NOT_FOUND = 0

    private CustomerQueryService queryService
    def customerViewQueryRepository = Mock(CustomerViewQueryRepository)

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        queryService = new DefaultCustomerQueryService(customerViewQueryRepository)
    }

    def "Success::getAllcustomer IDs are logged whenever they are saved in the DB"() {
        given: "all customer mock "
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final CustomerViewQuery customerViewQuery = customerViewQuery(emailId, firstName, lastName, age)
        customerViewQuery.action = Action.UPDATED

        customerViewQueryRepository.findAll(_ as Pageable) >> new PageImpl<>(
                List.of(customerViewQuery)
        )
        when: "that customer is saved in the DB"
        CustomerListResponseDTO customerDTOList = queryService.getAllCustomer(
                DEFAULT_PAGE_NUMBER,
                DEFAULT_SIZE_NUMBER
        )

        then: "the ID is correctly logged"
        customerDTOList.count == CUSTOMER_TOTAL_COUNT
        CustomerDTO customerDTO = customerDTOList?.customer?.get(FIRST_INDEX);
        customerDTO?.data?.emailId == emailId
        customerDTO?.data?.age == age
    }

    def "Failed::getAllCustomer IDs are logged whenever they are saved in the DB"() {
        given: "all Customer mock"

        def actualErrorMessage = null

        final PageRequest customerPage = PageRequest.of(
                DEFAULT_PAGE_NUMBER,
                DEFAULT_SIZE_NUMBER
        );
        customerViewQueryRepository.findAll(_ as Pageable) >> new PageImpl<>(
                Collections.emptyList(),
                customerPage,
                TOTAL_RECORD_NOT_FOUND
        )

        when: "that Customer is saved in the DB"
        try {
            queryService.getAllCustomer(DEFAULT_PAGE_NUMBER, DEFAULT_SIZE_NUMBER)
        } catch (Exception e) {
            actualErrorMessage = e.getMessage()
        }
        then: "the ID is correctly logged"
        actualErrorMessage == expectedErrorMessage

        where:
        testStep          | mockDBCustomerData      || expectedErrorMessage
        "No record found" | Collections.emptyList() || ERROR_NO_RECORD_FOUND
    }

    def "Success::getCustomerById IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns an ID to customer"
        def customerId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def customerIdUUID = UUID.fromString(customerId)
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final CustomerViewQuery customerViewQuery = customerViewQuery(emailId, firstName, lastName, age)
        customerViewQuery.customerId = customerIdUUID
        customerViewQuery.action = Action.UPDATED

        customerViewQueryRepository.findByCustomerId(_ as UUID) >> Optional.of(customerViewQuery)

        when: "that customer is saved in the DB"
        CustomerDTO customerDTO = queryService.getCustomerById(customerId)

        then: "the ID is correctly logged"
        customerDTO.customerId == customerIdUUID
        customerDTO?.data?.emailId == emailId
        customerDTO?.data?.age == age
    }

    def "Failed::getcustomerById IDs are logged whenever they are saved in the DB"() {
        given: "a customer that assigns an ID to customer"

        def actualErrorMessage = null
        customerViewQueryRepository.findByCustomerId(_ as UUID) >> mockDBCustomerData

        when: "that customer is saved in the DB"
        try {
            queryService.getCustomerById(customerId)
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
