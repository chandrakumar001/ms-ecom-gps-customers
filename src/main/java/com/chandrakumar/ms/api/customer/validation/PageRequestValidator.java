package com.chandrakumar.ms.api.customer.validation;

import java.util.Optional;

public class PageRequestValidator {

    private PageRequestValidator() {
        throw new IllegalStateException("PageRequestValidator class");
    }

    public static final String THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE = "Page.must.not.zero.negative";
    public static final String THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE = "Size.must.not.zero.negative";

    public static Optional<String> validateRequest(final Integer page,
                                                   final Integer size) {
        if (page == null && size == null) {
            return Optional.empty();
        }
        if (null != size && 0 >= size) {
            return Optional.of(THE_SIZE_MUST_NOT_BE_ZERO_OR_NEGATIVE);
        }
        if (null != page && 0 >= page) {
            return Optional.of(THE_PAGE_MUST_NOT_BE_ZERO_OR_NEGATIVE);
        }
        return Optional.empty();
    }
}
