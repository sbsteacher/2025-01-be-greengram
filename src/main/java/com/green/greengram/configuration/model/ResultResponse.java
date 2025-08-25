package com.green.greengram.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultResponse<T> {
    private String message;
    private T result;
}
