package com.green.greengram.configuration.exception;

import lombok.*;
import org.springframework.validation.FieldError;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationError {
    private String field;
    private String message;

    public static ValidationError of(final FieldError fieldError) {
        return ValidationError.builder()
                              .field(fieldError.getField())
                              .message(fieldError.getDefaultMessage())
                              .build();
    }

    @Override
    public String toString() {
        return String.format("field: %s, message: %s", field, message);
    }
}


