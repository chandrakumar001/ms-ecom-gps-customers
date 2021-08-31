package com.chandrakumar.ms.api.customer.service;

import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;

/**
 * Provides access to 'com.chandrakumar.ms.api.Customer.service' data
 *
 * @author chandrakumar ovanan
 */
public interface CustomerCommandService {

    /**
     * Saves the Customer.
     *
     * @param customerBareDTO the details of the Customer
     */
    CustomerDTO createCustomer(final CustomerBareDTO customerBareDTO);

    /**
     * Update a Customer by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.error.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.error.ResourceNotFoundException} will be thrown.
     *
     * @param customerId      the unique id of the Customer
     * @param customerBareDTO the object of the Customer
     * @return the CustomerDTO details
     */
    CustomerDTO updateCustomer(final String customerId,
                               final CustomerBareDTO customerBareDTO);

    /**
     * Delete a Customer by the id.
     * If the id is not uuid format, a {@link com.chandrakumar.ms.api.error.FieldValidationException} will be thrown.
     * If the id does not exist, a {@link com.chandrakumar.ms.api.error.ResourceNotFoundException} will be thrown.
     *
     * @param customerId the unique id of the Customer
     */
    void deleteCustomer(final String customerId);
}