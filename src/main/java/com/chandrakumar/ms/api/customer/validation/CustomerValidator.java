package com.chandrakumar.ms.api.customer.validation;

import com.chandrakumar.ms.api.customer.swagger.model.CustomerBareDTO;

import java.util.Optional;

import static com.chandrakumar.ms.api.customer.util.CustomerErrorCodeConstant.*;
import static com.chandrakumar.ms.api.util.CommonUtil.notValidateEmail;

public class CustomerValidator {

    private CustomerValidator() {
        throw new IllegalStateException("CustomerValidator class");
    }

    private static final int DEFAULT_AGE_ELIGIBLE = 18;

    public static Optional<String> validateCustomerDTO(final CustomerBareDTO customerBareDTO) {

        if (notValidateEmail(customerBareDTO.getEmailId())) {
            return Optional.of(ERROR_EMAIL_ID_IS_INVALID_FORMAT);
        }

        try {
            Integer.parseInt(customerBareDTO.getAge());
        } catch (NumberFormatException e) {
            return Optional.of(NUMBER_FORMAT_EXCEPTION_OCCURRED);
        }
        if (DEFAULT_AGE_ELIGIBLE > Integer.parseInt(customerBareDTO.getAge())) {
            return Optional.of(ERROR_THE_AGE_MUST_BE_18);
        }
        return Optional.empty();
    }
}
