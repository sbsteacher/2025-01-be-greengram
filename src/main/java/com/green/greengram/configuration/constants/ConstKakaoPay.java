package com.green.greengram.configuration.constants;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "constants.pay.kakao.data")
@RequiredArgsConstructor
public class ConstKakaoPay {
    public final String approvalUrl;
    public final String authorizationName;
    public final String cancelUrl;
    public final String cid;
    public final String failUrl;
    public final String kakaoPayInfoSessionName;
    public final String secretKey;
}
