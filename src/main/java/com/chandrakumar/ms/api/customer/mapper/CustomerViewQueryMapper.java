package com.chandrakumar.ms.api.customer.mapper;

import com.chandrakumar.ms.api.customer.entity.CustomerName;
import com.chandrakumar.ms.api.customer.entity.CustomerViewQuery;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerNameDTO;

import java.util.List;

public class CustomerViewQueryMapper {

    private CustomerViewQueryMapper() {
        throw new IllegalStateException("CustomerViewQueryMapper class");
    }

    public static CustomerListResponseDTO getCustomerListResponseDTO(
            final List<CustomerDTO> customerDTOList) {

        CustomerListResponseDTO customerListResponseDTO = new CustomerListResponseDTO();
        customerListResponseDTO.setCount(customerDTOList.size());
        customerListResponseDTO.setCustomer(customerDTOList);
        return customerListResponseDTO;
    }

    public static CustomerDTO mapToCustomerDTO(final CustomerViewQuery customerViewQuery) {

        final CustomerNameDTO customerNameDTO = mapToCustomerNameDTO(
                customerViewQuery.getCustomerName()
        );
        final CustomerBareDTO customerBareDTO = mapToCustomerBareDTO(
                customerViewQuery,
                customerNameDTO
        );
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setData(customerBareDTO);
        customerDTO.setCustomerId(customerViewQuery.getCustomerId());
        return customerDTO;
    }

    private static CustomerBareDTO mapToCustomerBareDTO(final CustomerViewQuery customerViewQuery,
                                                        final CustomerNameDTO customerNameDTO) {

        CustomerBareDTO customerBareDTO = new CustomerBareDTO();
        customerBareDTO.setEmailId(customerViewQuery.getEmailId());
        customerBareDTO.setAge(customerViewQuery.getAge());
        customerBareDTO.setFavouriteColour(customerViewQuery.getFavouriteColour());
        customerBareDTO.setCustomerName(customerNameDTO);
        return customerBareDTO;
    }

    private static CustomerNameDTO mapToCustomerNameDTO(final CustomerName existingCustomerName) {

        CustomerNameDTO customerNameDTO = new CustomerNameDTO();
        customerNameDTO.setFirstName(existingCustomerName.getFirstName());
        customerNameDTO.setLastName(existingCustomerName.getLastName());
        return customerNameDTO;
    }
}
