package com.green.greengram.config.enumcode;

import com.fasterxml.jackson.annotation.JsonValue;

public interface EnumMapperType {
    String getCode();
    @JsonValue
    String getValue();
}
