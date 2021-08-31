package com.chandrakumar.ms.api.interceptor;

import com.chandrakumar.ms.api.error.HttpHeaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class HttpHeaderInterceptor extends HandlerInterceptorAdapter {


    public static final String REQUIRED_REQUEST_BODY_IS_MISSING = "Required request body is missing";
    public static final String REQUIRED_CONTENT_TYPE_IS_MISSING = "Required content-Type is missing";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=UTF-8";
    public static final int DEFAULT_CONTENT_LENGTH_TWO = 2;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {

        final String method = request.getMethod();

        if (isEqualPostAndPutMethod(method)) {
            validateContentTypeAndGetErrorMessage(request)
                    .ifPresent(HttpHeaderException::httpHeaderExceptionException);
        }
        return true;
    }

    private Optional<String> validateContentTypeAndGetErrorMessage(
            final HttpServletRequest request) {

        final String contentType = request.getContentType();

        if (Objects.isNull(contentType)) {
            return Optional.of(REQUIRED_CONTENT_TYPE_IS_MISSING);
        }
        if (!contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) &&
                !contentType.equalsIgnoreCase(APPLICATION_JSON_CHARSET_UTF_8)) {
            return Optional.of(REQUIRED_CONTENT_TYPE_IS_MISSING);
        }

        final int contentLength = request.getContentLength();
        if (contentLength <= DEFAULT_CONTENT_LENGTH_TWO) {
            return Optional.of(REQUIRED_REQUEST_BODY_IS_MISSING);
        }
        return Optional.empty();
    }

    private boolean isEqualPostAndPutMethod(String method) {
        return method.equalsIgnoreCase(HttpMethod.POST.toString())
                || method.equalsIgnoreCase(HttpMethod.PUT.toString());
    }
}