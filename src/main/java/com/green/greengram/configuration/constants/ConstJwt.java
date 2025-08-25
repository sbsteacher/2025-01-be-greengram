package com.green.greengram.configuration.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "constants.jwt")
@RequiredArgsConstructor
@ToString
public class ConstJwt {
    private final String issuer;
    private final String bearerFormat;

    private final String claimKey;
    private final String secretKey;

    private final String accessTokenCookieName;
    private final String accessTokenCookiePath;
    private final int accessTokenCookieValiditySeconds;
    private final long accessTokenValidityMilliseconds;

    private final String refreshTokenCookieName;
    private final String refreshTokenCookiePath;
    private final int refreshTokenCookieValiditySeconds;
    private final long refreshTokenValidityMilliseconds;

}
