package neo.project.task.deal.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static ResponseEntity<Map<String, String>> buildResponse(String ex, HttpStatus serviceUnavailable) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex);
        return ResponseEntity.status(serviceUnavailable).body(response);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        log.error("RuntimeException: {}", ex.getMessage(), ex);
        buildResponse(ex.getMessage(),HttpStatus.NOT_FOUND);
        return null;
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, String>> handleRestClientException(RestClientException ex) {
        log.error("RestClientException: {}", ex.getMessage(), ex);
        buildResponse(ex.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        buildResponse(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        buildResponse(ex.getMessage(),HttpStatus.BAD_REQUEST);
        return null;
    }

}