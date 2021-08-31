package com.chandrakumar.ms.api.customer.service;

import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO;

/**
 * Provides access to 'com.chandrakumar.ms.api.customer.service' data
 *
 * @author chandrakumar ovanan
 */
public interface CustomerQueryService {

    /**
     * Retrieves all Customer.
     * If the Customer list does not exist, a {@link com.chandrakumar.ms.api.error.NoRecordFoundException} will be thrown.
     *
     * @return the CustomerListResponseDTO details
     */
    CustomerListResponseDTO getAllCustomer(final Integer page,
                                           final Integer size);

    /**
     * Retrieves a Customer by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.error.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.error.ResourceNotFoundException} will be thrown.
     *
     * @param customerId the unique id of the Customer
     * @return the CustomerDTO details
     */
    CustomerDTO getCustomerById(final String customerId);
}