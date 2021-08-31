package com.chandrakumar.ms.api.customer.service;

import com.chandrakumar.ms.api.customer.entity.Customer;
import com.chandrakumar.ms.api.error.FieldValidationException;
import com.chandrakumar.ms.api.error.ResourceAlreadyFoundException;
import com.chandrakumar.ms.api.error.ResourceNotFoundException;
import com.chandrakumar.ms.api.customer.repository.CustomerRepository;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.chandrakumar.ms.api.common.audit.Action.CREATED;
import static com.chandrakumar.ms.api.common.audit.Action.UPDATED;
import static com.chandrakumar.ms.api.customer.mapper.CustomerCommandMapper.mapToCustomer;
import static com.chandrakumar.ms.api.customer.mapper.CustomerCommandMapper.mapToCustomerDTO;
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.*;
import static com.chandrakumar.ms.api.customer.validation.CustomerValidator.validateCustomerDTO;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Transactional
@Slf4j
public class DefaultCustomerCommandService implements CustomerCommandService {

    private static final String UNKNOWN = "unknown";

    private final CustomerRepository customerRepository;
    private final AuditorAware<String> auditorAware;

    public DefaultCustomerCommandService(
            @Autowired final CustomerRepository customerRepository,
            final AuditorAware<String> auditorAware) {

        this.customerRepository = customerRepository;
        this.auditorAware = auditorAware;
    }

    @Override
    public CustomerDTO createCustomer(final CustomerBareDTO customerBareDTO) {
        log.info("called createCustomer begin");

        validateCustomerDTO(customerBareDTO)
                .ifPresent(FieldValidationException::fieldValidationException);

        existingCustomerByEmailId(customerBareDTO.getEmailId())
                .ifPresent(this::customerAlreadyFoundException);

        final Customer customer = mapToCustomer(customerBareDTO, new Customer());
        customer.setCustomerId(UUID.randomUUID());
        customer.setAction(CREATED);
        final Customer newCustomer = customerRepository.save(customer);
        log.info("called createCustomer end");
        return mapToCustomerDTO(newCustomer);
    }


    @Override
    public CustomerDTO updateCustomer(final String customerId,
                                      final CustomerBareDTO customerBareDTO) {
        log.info("called updateCustomer begin");

        validateUUID(customerId, ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(FieldValidationException::fieldValidationException);
        final UUID customerIdUUID = UUID.fromString(customerId);

        validateCustomerDTO(customerBareDTO)
                .ifPresent(FieldValidationException::fieldValidationException);

        final Customer existingCustomer = existingCustomerById(customerIdUUID);
        mapToCustomer(customerBareDTO, existingCustomer);
        existingCustomer.setAction(UPDATED);

        final Customer updatedCustomerDB = customerRepository.save(
                existingCustomer
        );
        log.info("called updateCustomer end");
        return mapToCustomerDTO(updatedCustomerDB);
    }

    @Override
    public void deleteCustomer(final String customerId) {
        log.info("called deleteCustomer begin");

        validateUUID(customerId, ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(FieldValidationException::fieldValidationException);
        final UUID customerIdUUID = UUID.fromString(customerId);

        existingCustomerById(customerIdUUID);

        final String name = auditorAware.getCurrentAuditor()
                .orElse(UNKNOWN);
        customerRepository.softDeleteByCustomerId(
                customerIdUUID,
                new Date(),
                name
        );
        log.info("called deleteCustomer end");
    }

    private Customer existingCustomerById(final UUID customerId) {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(this::customerNotFoundException);
    }

    private Optional<Customer> existingCustomerByEmailId(final String emailId) {
        return customerRepository.findByEmailId(emailId);
    }

    private RuntimeException customerNotFoundException() {
        return new ResourceNotFoundException(ERROR_CUSTOMER_ID_IS_NOT_FOUND);
    }

    private void customerAlreadyFoundException(Customer customer) {
        throw new ResourceAlreadyFoundException(ERROR_THE_EMAIL_IS_ALREADY_EXISTS);
    }
}