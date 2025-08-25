package com.green.greengram.configuration.enumcode;

import com.fasterxml.jackson.annotation.JsonValue;

/*
@JsonValue
ENUM을 직렬화 할 때 getValue의 리턴값으로 한다.
@RequestBody로 데이터를 받을 때, getValue의 값과 일치하는 ENUM으로 변환처리 해준다.
*/
public interface EnumMapperType {
    String getCode();
    @JsonValue
    String getValue();
}
