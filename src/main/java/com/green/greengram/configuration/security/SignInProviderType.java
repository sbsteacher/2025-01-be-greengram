package com.green.greengram.configuration.security;

import com.green.greengram.configuration.enumcode.AbstractEnumCodeConverter;
import com.green.greengram.configuration.enumcode.EnumMapperType;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SignInProviderType implements EnumMapperType {
      LOCAL("01", "로컬")
    , KAKAO("02", "카카오")
    , NAVER("03", "네이버")
    , GOOGLE("04", "구글");

    private final String code;
    private final String value;

    @Converter(autoApply = true)
    public static class CodeConverter extends AbstractEnumCodeConverter<SignInProviderType> {
        public CodeConverter() {
            super(SignInProviderType.class, false);
        }
    }
}
