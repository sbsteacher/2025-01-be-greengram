package com.green.greengram.configuration.enumcode;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractEnumCodeConverter<E extends Enum<E> & EnumMapperType>
        implements AttributeConverter<E, String> {
    private final Class<E> targetEnumClass;
    private final boolean nullable; //Null 허용 여부

    //DB에 Enum값을 insert할 때 사용
    @Override
    public String convertToDatabaseColumn(E enumItem) {
        if(!nullable && enumItem == null) { //null허용을 하지 않았는데 enum(값)이 없다면
            throw new IllegalArgumentException(String.format("%s(는)은 NULL로 저장할 수 없습니다."
                    , targetEnumClass.getSimpleName()));
        }
        return EnumConvertUtils.toCode(enumItem);
    }

    //DB에서 Enum값을 select 때 사용
    @Override
    public E convertToEntityAttribute(String dbData) {
        if(!nullable && StringUtils.isBlank(dbData) || dbData == null) {
            throw new IllegalArgumentException(String.format("%s(는)은 DB에 NULL 혹은 Empty로 저장되어 있습니다."
                    , targetEnumClass.getSimpleName()));
        }
        return EnumConvertUtils.ofCode(targetEnumClass, dbData);
    }
}
