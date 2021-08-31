package com.chandrakumar.ms.api.customer.resource

import com.chandrakumar.ms.api.customer.service.CustomerCommandService
import com.chandrakumar.ms.api.customer.service.CustomerMockData
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.customer.service.CustomerMockData.customerBareDTO
import static com.chandrakumar.ms.api.customer.service.CustomerMockData.customerDTO
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@Unroll
// No @ContextConfiguration with Spring MVC Test!
class DefaultCustomerCommandControllerSpec extends Specification {

    // Don't want to call real service, so use mock
    def customerCommandService = Mock(CustomerCommandService)
    ObjectMapper objectMapper;
    MockMvc mockMvc = null;

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        objectMapper = new ObjectMapper()
        def customerCommandRestController = new CustomerCommandRestController(
                customerCommandService
        )
        // Let Spring MVC Test process the controller:
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerCommandRestController)
                .build()
    }

    def "should delete Customer object"() {
        given:

        customerCommandService.deleteCustomer(_ as String) >> null
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = delete(
                "/v1/customers/d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        );
        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        1 * customerCommandService.deleteCustomer(_ as String) >> null
    }

    def "should create Customer object"() {
        given:
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final CustomerBareDTO customerBareDTO = customerBareDTO(emailId, firstName, lastName, age)
        customerCommandService.createCustomer(_ as CustomerBareDTO) >> customerDTO(
                emailId,
                firstName,
                lastName,
                age
        )
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = post("/v1/customers")
                .content(asJsonString(customerBareDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        1 * customerCommandService.createCustomer(_ as CustomerBareDTO) >> customerDTO(
                emailId,
                firstName,
                lastName,
                age
        )
    }

    def "should update Customer object"() {
        given:
        def customerId = "d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        def emailId = "osaimar19@gmail.com"
        def firstName = "chandra"
        def lastName = "kumar"
        def age = "28"

        final CustomerBareDTO customerBareDTO = customerBareDTO(emailId, firstName, lastName, age)
        customerCommandService.updateCustomer(_ as String, _ as CustomerBareDTO) >> customerDTO(
                emailId,
                firstName,
                lastName,
                age
        )
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = put("/v1/customers/" + customerId)
                .content(asJsonString(customerBareDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        1 * customerCommandService.updateCustomer(_ as String, _ as CustomerBareDTO) >> customerDTO(
                emailId,
                firstName,
                lastName,
                age
        )
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
