package com.green.greengram.application.pay.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoPaySessionDto {
    private String tid;
    private String partnerOrderId;
    private String partnerUserId;
}
