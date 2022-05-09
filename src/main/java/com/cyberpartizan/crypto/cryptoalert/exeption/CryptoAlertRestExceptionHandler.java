package com.cyberpartizan.crypto.cryptoalert.exeption;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class CryptoAlertRestExceptionHandler {

    private final ErrorAttributes errorAttributes;
    private final ServerProperties serverProperties;
//    private final ResourceBundleMessageSource messages;

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(Exception e, HttpServletRequest request) {
        return getResponseEntity(request, e.getMessage(), HttpStatus.BAD_REQUEST, e, e.getMessage());
    }

    private ResponseEntity<Map<String, Object>> getResponseEntity(HttpServletRequest request, String errorMessage,
                                                                  HttpStatus status, Exception ex, String logMessage) {
        log.error(logMessage, ex);

        var webRequest = new ServletWebRequest(request);
        var options = ErrorAttributeOptions.defaults();
        if (serverProperties.getError().getIncludeStacktrace().equals(ErrorProperties.IncludeStacktrace.ALWAYS)) {
            options = options.including(ErrorAttributeOptions.Include.STACK_TRACE);
        }
        var properties = errorAttributes.getErrorAttributes(webRequest, options);

        properties.put("message", errorMessage);
        properties.put("status", status.value());
        properties.put("error", status.getReasonPhrase());
        properties.put("path", request.getRequestURI());
        return new ResponseEntity<>(properties, status);
    }
}
