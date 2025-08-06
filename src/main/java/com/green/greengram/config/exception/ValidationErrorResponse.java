package com.green.greengram.config.exception;

import com.green.greengram.config.model.ResultResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

public class ValidationErrorResponse extends ResultResponse<String> {

    public ValidationErrorResponse(String message, String result) {
        super(message, result);
    }


    @Getter
    @Builder
    public static class ValidationError {
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

}
