package com.green.greengram.configuration.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.naver")
public class ConstOauth2Naver {
    public final String clientId;
    public final String clientSecret;
}
