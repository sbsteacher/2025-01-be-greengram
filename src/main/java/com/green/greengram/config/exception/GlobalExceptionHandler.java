package com.green.greengram.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.config.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper objectMapper;

    //Validation 예외가 발생되었을 때 캐치
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
                                                                , HttpHeaders headers
                                                                , HttpStatusCode statusCode
                                                                , WebRequest request) {
        List<ValidationErrorResponse.ValidationError> errors = getValidationError(ex);
        List<String> messages = errors.stream().map(item -> item.getMessage()).toList();
        StringBuilder sb = new StringBuilder();
        for(String message : messages){
            sb.append(message);
            sb.append("\n");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ResultResponse<>(sb.toString(), errors.toString()));
    }

    private List<ValidationErrorResponse.ValidationError> getValidationError(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return fieldErrors.stream().map(item -> ValidationErrorResponse.ValidationError.of(item)).toList();
    }

}
