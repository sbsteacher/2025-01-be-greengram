package com.green.greengram.configuration.enumcode;

import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.EnumSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumConvertUtils {
    //String(Code값) to Enum
    //리턴타입이 Enum이어야 하고 EnumMapperType을 상속받은 Enum이어야 한다.
    //enumClass는 Enum타입이어야 한다.
    public static <E extends Enum<E> & EnumMapperType> E ofCode(Class<E> enumClass, String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        } //code 매개변수가 null이거나 빈칸인 경우 return null처리

        //Enum에 있는 값 중 매개변수 code와 같은 값을 찾아 리턴하기 위함. 근데 같은게 없으면 return null
        return EnumSet.allOf(enumClass).stream() //enum을 Stream화 하기 위함
                .filter(item -> item.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    //Enum to String(Code값)
    public static <E extends Enum<E> & EnumMapperType> String toCode(E enumItem) {
        if (enumItem == null) { return null; }
        return enumItem.getCode();
    }
}
