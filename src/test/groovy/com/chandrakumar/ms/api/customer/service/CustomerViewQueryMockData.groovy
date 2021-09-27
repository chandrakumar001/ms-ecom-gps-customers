package com.chandrakumar.ms.api.customer.service


import com.chandrakumar.ms.api.customer.entity.CustomerName
import com.chandrakumar.ms.api.customer.entity.CustomerQueryView
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO
import com.chandrakumar.ms.api.customer.swagger.model.CustomerNameDTO

class CustomerViewQueryMockData {

    static CustomerDTO customerDTO(String emailId,
                                   String firstName,
                                   String lastName,
                                   String age) {
        CustomerBareDTO customerBareDTO = customerBareDTO(
                emailId,
                firstName,
                lastName,
                age
        )
        CustomerDTO customerDTO = new CustomerDTO()
        customerDTO.customerId = UUID.fromString("d6f02a17-c676-4b1b-ae39-e3b12f47c407")
        customerDTO.data = customerBareDTO
        customerDTO
    }

    static CustomerBareDTO customerBareDTO(String emailId,
                                           String firstName,
                                           String lastName,
                                           String age) {

        CustomerNameDTO customerNameDTO = customerNameDTO(
                firstName,
                lastName
        )
        CustomerBareDTO customerBareDTO = new CustomerBareDTO()
        customerBareDTO.emailId = emailId
        customerBareDTO.customerName = customerNameDTO
        customerBareDTO.age = age
        customerBareDTO.favouriteColour = "blue"
        customerBareDTO
    }

    private static CustomerNameDTO customerNameDTO(String firstName,
                                                   String lastName) {

        CustomerNameDTO customerNameDTO = new CustomerNameDTO()
        customerNameDTO.firstName = firstName
        customerNameDTO.lastName = lastName
        customerNameDTO
    }

    static CustomerQueryView customerViewQuery(String emailId,
                                               String firstName,
                                               String lastName,
                                               String age) {

        CustomerName customerName = customerName(
                firstName,
                lastName
        )
        CustomerQueryView customer = new CustomerQueryView()
        customer.emailId = emailId
        customer.customerName = customerName
        customer.age = age
        customer.favouriteColour = "blue"
        customer
    }

    private static CustomerName customerName(String firstName,
                                             String lastName) {

        CustomerName customerName = new CustomerName()
        customerName.firstName = firstName
        customerName.lastName = lastName
        customerName
    }

    static List<CustomerDTO> customerDTOs() {

        final CustomerDTO customerDTO1 = customerDTO(
                "abc@in.com",
                "abc",
                "c",
                String.valueOf(18)
        )
        final CustomerDTO customerDTO2 = customerDTO(
                "chandrakumar@in.com",
                "chandra",
                "kumar",
                String.valueOf(28)
        )

        return List.of(customerDTO1, customerDTO2)
    }
}
