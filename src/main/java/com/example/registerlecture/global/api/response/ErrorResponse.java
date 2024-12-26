package com.example.registerlecture.global.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값 필드는 JSON에서 제외
public class ErrorResponse {
    private final int statusCode;
    private final String message;

    @Builder
    ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static ErrorResponse of(int statusCode, String message) {
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    public static ErrorResponse of(int statusCode, BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            if (!isFirst) {
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("] ");
            sb.append(fieldError.getDefaultMessage());
        }
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .message(sb.toString())
                .build();
    }
}
