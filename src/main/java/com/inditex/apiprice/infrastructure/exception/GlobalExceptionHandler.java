package com.inditex.apiprice.infrastructure.exception;

import com.inditex.apiprice.domain.exception.PriceNotFoundException;
import com.inditex.apiprice.infrastructure.exception.dto.ErrorResponse;
import com.inditex.apiprice.infrastructure.util.MessageError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Map<Class<? extends Throwable>, Function<Throwable, ResponseEntity<ErrorResponse>>> exceptionHandlers =
            Map.ofEntries(
                    Map.entry(PriceNotFoundException.class, ex ->
                            createErrorResponse(MessageError.PRICE_NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND)),

                    Map.entry(MissingServletRequestParameterException.class, ex ->
                            createErrorResponse(MessageError.MISSING_REQUEST_PARAMETER, ex.getMessage(), HttpStatus.BAD_REQUEST)),

                    Map.entry(MethodArgumentTypeMismatchException.class, ex -> {
                        MethodArgumentTypeMismatchException e = (MethodArgumentTypeMismatchException) ex;
                        String msg = String.format(
                                MessageError.ARGUMENT_TYPE_MISMATCH_TEMPLATE,
                                e.getName(),
                                e.getValue(),
                                e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown"
                        );
                        return createErrorResponse(MessageError.ARGUMENT_TYPE_MISMATCH, msg, HttpStatus.BAD_REQUEST);
                    }),

                    Map.entry(HttpMessageNotReadableException.class, ex ->
                            createErrorResponse(MessageError.MALFORMED_JSON_REQUEST, ex.getMessage(), HttpStatus.BAD_REQUEST))
            );

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Throwable ex) {
        Function<Throwable, ResponseEntity<ErrorResponse>> handler = exceptionHandlers.getOrDefault(
                ex.getClass(),
                e -> createErrorResponse(MessageError.UNEXPECTED_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)
        );
        log.error("Exception caught: {}", ex.getMessage(), ex);
        return handler.apply(ex);
    }

    private static ResponseEntity<ErrorResponse> createErrorResponse(String error, String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(
                message,
                error,
                status.value(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}
