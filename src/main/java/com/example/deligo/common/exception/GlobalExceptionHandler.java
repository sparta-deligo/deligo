package com.example.deligo.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("Custom Exception [statusCode = {}, msg = {}]",
                e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid( /* @Valid 검증 오류 */
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ExceptionType exceptionType = ExceptionType.INVALID_REQUEST;

        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> "["+error.getField()+"] " + error.getDefaultMessage())
                .toList();

        log.error("Validation Exception [uri = {}, msg = {}]",
                request.getContextPath(), e.getMessage());
        return ResponseEntity.status(exceptionType.getHttpStatus())
                .body(new ErrorResponse(exceptionType, String.join(", ",errors)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Server Exception [msg = {}, cause = {}]",
                e.getMessage(), e.getCause());
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(ExceptionType.INTERNAL_SERVER_ERROR,
                        ExceptionType.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
