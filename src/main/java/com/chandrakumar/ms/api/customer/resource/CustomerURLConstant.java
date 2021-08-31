package com.chandrakumar.ms.api.customer.resource;

public class CustomerURLConstant {

    private CustomerURLConstant() {
        throw new IllegalStateException("CustomerURLConstant class");
    }

    public static final String CUSTOMER_ID_PATH = "customer-id";
    public static final String V1_CUSTOMERS_CUSTOMER_ID_URL = "v1/customers/{customer-id}";
    public static final String V1_CUSTOMER_URL = "v1/customers";
}
