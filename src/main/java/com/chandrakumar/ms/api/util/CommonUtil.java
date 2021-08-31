package com.chandrakumar.ms.api.util;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CommonUtil {

    private CommonUtil() {
        throw new IllegalStateException("CommonUtil class");
    }

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static Optional<String> validateUUID(final String uuid,
                                                final String errorMessage) {
        try {
            UUID.fromString(uuid);
            return Optional.empty();
        } catch (IllegalArgumentException illegalArgumentException) {
            return Optional.of(errorMessage);
        }
    }

    /**
     * This method validates the input email address with EMAIL_REGEX pattern
     *
     * @param email incoming request
     * @return boolean
     */
    public static boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean notValidateEmail(final String email) {
        return !validateEmail(email);
    }
}
