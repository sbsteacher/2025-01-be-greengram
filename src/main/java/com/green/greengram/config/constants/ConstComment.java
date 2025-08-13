package com.green.greengram.config.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "constants.comment")
@RequiredArgsConstructor
public class ConstComment {
    public final int startIndex;
    public final int needForViewCount;
}
