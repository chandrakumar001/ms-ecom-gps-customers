package com.chandrakumar.ms.api.customer.mapper;

import com.chandrakumar.ms.api.customer.entity.CustomerName;
import com.chandrakumar.ms.api.customer.entity.CustomerQueryView;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerNameDTO;

import java.util.List;

public class CustomerQueryViewMapper {

    private CustomerQueryViewMapper() {
        throw new IllegalStateException("CustomerQueryViewMapper class");
    }

    public static CustomerListResponseDTO getCustomerListResponseDTO(
            final List<CustomerDTO> customerDTOList) {

        CustomerListResponseDTO customerListResponseDTO = new CustomerListResponseDTO();
        customerListResponseDTO.setCount(customerDTOList.size());
        customerListResponseDTO.setCustomer(customerDTOList);
        return customerListResponseDTO;
    }

    public static CustomerDTO mapToCustomerDTO(final CustomerQueryView customerQueryView) {

        final CustomerNameDTO customerNameDTO = mapToCustomerNameDTO(
                customerQueryView.getCustomerName()
        );
        final CustomerBareDTO customerBareDTO = mapToCustomerBareDTO(
                customerQueryView,
                customerNameDTO
        );
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setData(customerBareDTO);
        customerDTO.setCustomerId(customerQueryView.getCustomerId());
        return customerDTO;
    }

    private static CustomerBareDTO mapToCustomerBareDTO(final CustomerQueryView customerQueryView,
                                                        final CustomerNameDTO customerNameDTO) {

        CustomerBareDTO customerBareDTO = new CustomerBareDTO();
        customerBareDTO.setEmailId(customerQueryView.getEmailId());
        customerBareDTO.setAge(customerQueryView.getAge());
        customerBareDTO.setFavouriteColour(customerQueryView.getFavouriteColour());
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
