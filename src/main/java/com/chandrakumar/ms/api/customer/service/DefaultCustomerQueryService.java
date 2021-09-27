package com.chandrakumar.ms.api.customer.service;

import com.chandrakumar.ms.api.customer.entity.CustomerQueryView;
import com.chandrakumar.ms.api.customer.mapper.CustomerQueryViewMapper;
import com.chandrakumar.ms.api.customer.repository.CustomerQueryViewRepository;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerDTO;
import com.chandrakumar.ms.api.customer.swagger.model.CustomerListResponseDTO;
import com.chandrakumar.ms.api.error.FieldValidationException;
import com.chandrakumar.ms.api.error.NoRecordFoundException;
import com.chandrakumar.ms.api.error.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.chandrakumar.ms.api.customer.mapper.CustomerQueryViewMapper.getCustomerListResponseDTO;
import static com.chandrakumar.ms.api.customer.mapper.CustomerQueryViewMapper.mapToCustomerDTO;
import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.*;
import static com.chandrakumar.ms.api.customer.util.PageRequestBuild.getPageRequest;
import static com.chandrakumar.ms.api.customer.validation.PageRequestValidator.validateRequest;
import static com.chandrakumar.ms.api.util.CommonUtil.validateUUID;

@Service
@Slf4j
public class DefaultCustomerQueryService implements CustomerQueryService {

    private final CustomerQueryViewRepository customerQueryViewRepository;

    public DefaultCustomerQueryService(@Autowired CustomerQueryViewRepository customerQueryViewRepository) {
        this.customerQueryViewRepository = customerQueryViewRepository;
    }

    @Override
    public CustomerListResponseDTO getAllCustomer(final Integer page,
                                                  final Integer size) {
        log.info("called getAllCustomer begin");

        validateRequest(page, size)
                .ifPresent(FieldValidationException::fieldValidationException);

        final PageRequest pageRequest = getPageRequest(
                page,
                size
        );

        final Page<CustomerQueryView> pageCustomer = customerQueryViewRepository.findAll(
                pageRequest
        );
        final List<CustomerDTO> customerDTOList = getCustomerDTOList(
                pageCustomer
        );
        log.info("called getAllCustomer end");
        return getCustomerListResponseDTO(customerDTOList);
    }

    private List<CustomerDTO> getCustomerDTOList(final Page<CustomerQueryView> pageCustomer) {

        final List<CustomerQueryView> customerList = pageCustomer.getContent();
        if (CollectionUtils.isEmpty(customerList)) {
            throw new NoRecordFoundException(ERROR_NO_RECORD_FOUND);
        }
        return customerList.stream()
                .map(CustomerQueryViewMapper::mapToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(final String customerId) {
        log.info("called getCustomerById begin");

        validateUUID(customerId, ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT)
                .ifPresent(FieldValidationException::fieldValidationException);
        final UUID customerIdUUID = UUID.fromString(customerId);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final CustomerQueryView existingCustomer = existingCustomerById(customerIdUUID);
        stopWatch.stop();
        log.info("Execution time of " + stopWatch.getTotalTimeMillis() + "ms");

        log.info("called getCustomerById end");
        return mapToCustomerDTO(existingCustomer);
    }

    private CustomerQueryView existingCustomerById(final UUID customerId) {
        return customerQueryViewRepository.findByCustomerId(customerId)
                .orElseThrow(DefaultCustomerQueryService::customerNotFoundException);
    }

    private static RuntimeException customerNotFoundException() {
        return new ResourceNotFoundException(ERROR_CUSTOMER_ID_IS_NOT_FOUND);
    }
}