package com.chandrakumar.ms.api.customer.mapper;

import com.chandrakumar.ms.api.customer.entity.Customer;
import com.chandrakumar.ms.api.customer.entity.CustomerName;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerNameDTO;

public class CustomerCommandMapper {

    private CustomerCommandMapper() {
        throw new IllegalStateException("CustomerCommandMapper class");
    }

    public static CustomerDTO mapToCustomerDTO(final Customer existingCustomer) {

        final CustomerNameDTO customerNameDTO = mapToCustomerNameDTO(
                existingCustomer.getCustomerName()
        );
        final CustomerBareDTO customerBareDTO = mapToCustomerBareDTO(
                existingCustomer,
                customerNameDTO
        );
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setData(customerBareDTO);
        customerDTO.setCustomerId(existingCustomer.getCustomerId());
        return customerDTO;
    }

    private static CustomerBareDTO mapToCustomerBareDTO(final Customer existingCustomer,
                                                        final CustomerNameDTO customerNameDTO) {

        CustomerBareDTO customerBareDTO = new CustomerBareDTO();
        customerBareDTO.setEmailId(existingCustomer.getEmailId());
        customerBareDTO.setAge(existingCustomer.getAge());
        customerBareDTO.setFavouriteColour(existingCustomer.getFavouriteColour());
        customerBareDTO.setCustomerName(customerNameDTO);
        return customerBareDTO;
    }

    private static CustomerNameDTO mapToCustomerNameDTO(final CustomerName existingCustomerName) {

        CustomerNameDTO customerNameDTO = new CustomerNameDTO();
        customerNameDTO.setFirstName(existingCustomerName.getFirstName());
        customerNameDTO.setLastName(existingCustomerName.getLastName());
        return customerNameDTO;
    }

    public static Customer mapToCustomer(final CustomerBareDTO customerBareDTO,
                                         final Customer customer) {

        final CustomerName customerName = mapToCustomerName(
                customerBareDTO.getCustomerName()
        );
        customer.setEmailId(customerBareDTO.getEmailId());
        customer.setAge(customerBareDTO.getAge());
        customer.setFavouriteColour(customerBareDTO.getFavouriteColour());
        customer.setCustomerName(customerName);
        return customer;
    }

    private static CustomerName mapToCustomerName(final CustomerNameDTO customerNameDTO) {

        CustomerName customerName = new CustomerName();
        customerName.setFirstName(customerNameDTO.getFirstName());
        customerName.setLastName(customerNameDTO.getLastName());
        return customerName;
    }

}
