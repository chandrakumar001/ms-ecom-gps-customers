package com.chandrakumar.ms.api.customer.util;

public class CustomerErrorCodeConstant {

    private CustomerErrorCodeConstant() {
        throw new IllegalStateException("CustomerErrorCodeConstant class");
    }

    public static final String NUMBER_FORMAT_EXCEPTION_OCCURRED = "Invalid.Number.format";
    public static final String ERROR_EMAIL_ID_IS_INVALID_FORMAT = "Invalid.format.customerBareDTO.EmailId";
    public static final String ERROR_THE_AGE_MUST_BE_18 = "Eligible.customerBareDTO.Age";
    public static final String ERROR_THE_CUSTOMER_ID_IS_INVALID_UUID_FORMAT = "Invalid.format.customer.customerId";
    public static final String ERROR_CUSTOMER_ID_IS_NOT_FOUND = "NotFound.customer.customerId";
    public static final String ERROR_THE_EMAIL_IS_ALREADY_EXISTS = "AlreadyFound.customer.email";
    public static final String ERROR_NO_RECORD_FOUND = "NoRecord.found.list";
}