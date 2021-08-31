package com.chandrakumar.ms.api.customer.resource


import com.chandrakumar.ms.api.customer.service.CustomerQueryService
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

import static com.chandrakumar.ms.api.customer.service.CustomerMockData.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@Unroll
// No @ContextConfiguration with Spring MVC Test!
class DefaultCustomerQueryControllerSpec extends Specification {

    // Don't want to call real service, so use mock
    def queryService = Mock(CustomerQueryService)
    ObjectMapper objectMapper;
    def mockMvc = null;

    /**
     * Runs before each test method, like the JUnit Before
     * annotation
     */
    def setup() {
        objectMapper = new ObjectMapper()
        def customerQueryRestController = new CustomerQueryRestController(
                queryService
        )
        // Let Spring MVC Test process the controller:
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerQueryRestController)
                .build()
    }

    void "should return getAllCustomer"() {
        given:

        List<CustomerDTO> customerDTOList = customerDTOs()
        final CustomerListResponseDTO customerListResponseDTO = new CustomerListResponseDTO()
        customerListResponseDTO.customer = customerDTOList
        customerListResponseDTO.count = customerDTOList.size()

        queryService.getAllCustomer(_ as Integer, _ as Integer) >> customerListResponseDTO

        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = get(
                "/v1/customers?page=1&size=10"
        );
        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        final String actualResponse = mvcResult.getResponse().getContentAsString();
        final List<CustomerDTO> customerDTOS = actualResponseBody(actualResponse)

        final String actualResponseBody = objectMapper.writeValueAsString(customerDTOS)
        final String exceptedResponseBody = objectMapper.writeValueAsString(customerDTOList)
        and:
        actualResponseBody == exceptedResponseBody
        1 * queryService.getAllCustomer(_ as Integer, _ as Integer) >> customerListResponseDTO
    }

    def "should return customerDTO object"() {
        given:

        final CustomerDTO customerDTO = customerDTO(
                "abc@in.com",
                "abc",
                "c",
                String.valueOf(18)
        )

        queryService.getCustomerById(_ as String) >> customerDTO
        when:
        // Try different URL or Method to see what will happen!
        MockHttpServletRequestBuilder builder = get(
                "/v1/customers/d6f02a17-c676-4b1b-ae39-e3b12f47c407"
        );
        MvcResult mvcResult = mockMvc.perform(builder)
                .andReturn()

        then:
        final String actualResponseBody = mvcResult.getResponse().getContentAsString();
        final String exceptedResponseBody = objectMapper.writeValueAsString(customerDTO)

        and:
        actualResponseBody == exceptedResponseBody
        1 * queryService.getCustomerById(_ as String) >> customerDTO
    }


    private List<CustomerDTO> actualResponseBody(String actualResponse) {

        final CustomerListResponseDTO customerListResponseDTO = objectMapper.readValue(
                actualResponse,
                CustomerListResponseDTO.class
        )
        List<CustomerDTO> customerDTOList = customerListResponseDTO.getCustomer()
        customerDTOList
    }
}
